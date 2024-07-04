package org.team.bookshop.cart;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
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
import org.team.bookshop.domain.cart.controller.CartController;
import org.team.bookshop.domain.cart.dto.RequestVarifyCart;
import org.team.bookshop.domain.cart.service.CartService;

@WebMvcTest(controllers = CartController.class)
@MockBean(JpaMetamodelMappingContext.class)
public class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CartService cartService;

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
  public void testGetVerifyCart() throws Exception {
    // Given
    List<RequestVarifyCart> requestVerifyCarts = Arrays.asList(
        RequestVarifyCart.builder()
            .productId(1L)
            .price(3000)
            .quantity(2)
            .build(),
        RequestVarifyCart.builder()
            .productId(2L)
            .price(3000)
            .quantity(2)
            .build()
    );

    String selectedCartItems = objectMapper.writeValueAsString(requestVerifyCarts);

    doNothing().when(cartService).validateCart(any());

    // When & Then
    mockMvc.perform(get("/api/cart/verify")
            .param("selectedCartItems", selectedCartItems)
            .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
  }
}