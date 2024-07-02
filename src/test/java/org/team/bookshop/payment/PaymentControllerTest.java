package org.team.bookshop.payment;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.team.bookshop.domain.payment.controller.PaymentController;
import org.team.bookshop.domain.payment.dto.PaymentResponse;
import org.team.bookshop.domain.payment.dto.RequestPrevPayment;
import org.team.bookshop.domain.payment.dto.RequestCompletePayment;
import org.team.bookshop.domain.payment.dto.ResponsePrevPayment;
import org.team.bookshop.domain.payment.service.PaymentService;



@WebMvcTest(PaymentController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class PaymentControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PaymentService paymentService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .defaultRequest(post("/**").with(csrf()))
        .defaultRequest(put("/**").with(csrf()))
        .defaultRequest(delete("/**").with(csrf()))
        .build();
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  void testProcessPayment() throws Exception {
    // Given
    RequestPrevPayment request = RequestPrevPayment.builder()
        .merchantUid("merchant_1234")
        .amount(10000)
        .build();
    ResponsePrevPayment response = ResponsePrevPayment.builder()
        .merchantUid("merchant_1234")
        .amount(10000)
        .build();

    when(paymentService.preparePayment(any(RequestPrevPayment.class))).thenReturn(response);


    // When & Then
    mockMvc.perform(post("/api/payment/prepare")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.merchantUid").value("merchant_1234"))
        .andExpect(jsonPath("$.data.amount").value(10000));
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  void testCompletePayment() throws Exception {
    // Given
    RequestCompletePayment request = RequestCompletePayment.builder()
        .impUid("imp_123")
        .merchantUid("merchant_123")
        .build();

    PaymentResponse paymentResponse = PaymentResponse.builder()
        .amount(10000L)
        .build();

    when(paymentService.complatePayment(any(RequestCompletePayment.class)))
        .thenReturn(paymentResponse);

    // When & Then
    mockMvc.perform(post("/api/payment/completePayment")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.amount").value(10000L));
  }
}