package org.team.bookshop.order;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
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
import org.team.bookshop.domain.order.Service.OrderService;
import org.team.bookshop.domain.order.controller.OrderController;
import org.team.bookshop.domain.order.dto.request.OrderCreateRequest;
import org.team.bookshop.domain.order.dto.request.OrderUpdateRequest;
import org.team.bookshop.domain.order.dto.response.OrderAbstractResponse;
import org.team.bookshop.domain.order.dto.response.OrderCreateResponse;
import org.team.bookshop.domain.order.dto.response.OrderResponse;

@WebMvcTest(controllers = OrderController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class OrderControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderService orderService;

  @Autowired
  private ObjectMapper objectMapper;

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
  public void createOrder() throws Exception {
    // Given
    OrderCreateRequest request = new OrderCreateRequest();
    OrderCreateResponse response = new OrderCreateResponse();
    response.setOrderId(1L);

    when(orderService.save(any(OrderCreateRequest.class))).thenReturn(1L);
    when(orderService.findByIdForCreateResponse(anyLong())).thenReturn(response);

    // When & Then
    mockMvc.perform(post("/api/orders/create")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.orderId").value(1L));
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void updateOrder() throws Exception {
    // Given
    OrderUpdateRequest request = OrderUpdateRequest.builder()
        .merchantUid("gbs162")
        .build();
    OrderResponse response = OrderResponse.builder()
        .orderId(1L)
        .merchantUid("gbs162")
        .build();

    when(orderService.update(any(OrderUpdateRequest.class))).thenReturn("gbs162");
    when(orderService.getOrderDetail(any(String.class))).thenReturn(response);

    // When & Then
    mockMvc.perform(patch("/api/orders/update")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderId").value(1L))
        .andExpect(jsonPath("$.merchantUid").value("gbs162"));
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void deleteOrder() throws Exception {
    // Given
    when(orderService.delete(anyLong())).thenReturn(1L);

    // When & Then
    mockMvc.perform(delete("/api/orders/delete/{orderId}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void getUsersOrdersList() throws Exception {
    // Given
    List<OrderAbstractResponse> responseList = List.of(new OrderAbstractResponse());

    when(orderService.findByUserIdForOrderListResponse(anyLong())).thenReturn(responseList);

    // When & Then
    mockMvc.perform(get("/api/orders/user/{userId}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0]").exists());
  }

  @Test
  @WithMockUser(username = "user", roles = {"USER"})
  public void getOrderDetail() throws Exception {
    // Given
    OrderResponse response = new OrderResponse();

    when(orderService.getOrderDetail(any(String.class))).thenReturn(response);

    // When & Then
    mockMvc.perform(get("/api/orders/{merchantUid}", "merchantUid")
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }

}
