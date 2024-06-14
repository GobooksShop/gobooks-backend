package org.team.bookshop.domain.cart.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.team.bookshop.domain.cart.dto.RequestVarifyCart;
import org.team.bookshop.domain.cart.service.CartService;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/cart")
public class CartController {

  private final ObjectMapper objectMapper;
  private final CartService cartService;

  @GetMapping("/verify")
  public ResponseEntity<?> getVerifyCart(@RequestParam String selectedCartItems)
      throws JsonProcessingException {
    List<RequestVarifyCart> requestVarifyCarts = objectMapper.readValue(selectedCartItems,
        new TypeReference<>() {
        });
    log.info("정상적으로 변환 됐습니다. requestVarifyCarts: {}", requestVarifyCarts);
    cartService.validateCart(requestVarifyCarts);
    return ResponseEntity.ok().build();
  }
}
