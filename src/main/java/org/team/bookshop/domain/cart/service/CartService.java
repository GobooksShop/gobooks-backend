package org.team.bookshop.domain.cart.service;

import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.team.bookshop.domain.cart.dto.RequestVarifyCart;
import org.team.bookshop.domain.product.entity.Product;
import org.team.bookshop.domain.product.repository.ProductRepository;
import org.team.bookshop.global.error.ErrorCode;
import org.team.bookshop.global.error.exception.ApiException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

  private final ProductRepository productRepository;

  public void validateCart(List<RequestVarifyCart> requestVarifyCart) {

    Collections.sort(requestVarifyCart);
    List<Long> productIds = requestVarifyCart.stream()
        .map(RequestVarifyCart::getProductId)
        .toList();
    int cartTotalPrice = 0;
    int productTotalPrice = 0;

    List<Product> findProductList = productRepository.findByProductIds(productIds);

    //프론트에서 했는데 굳이 또 검증을 해야 할까?
    if (findProductList.isEmpty()) {
      throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    for (int i = 0; i < requestVarifyCart.size(); i++) {
      Product product = findProductList.get(i);
      RequestVarifyCart varifyCart = requestVarifyCart.get(i);

      //재고 검증
      if (product.getStockQuantity() - varifyCart.getQuantity() < 1) {
        //재고 검증 커스텀 에러
        throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
      }

      cartTotalPrice += varifyCart.getPrice() * varifyCart.getQuantity();
      productTotalPrice += product.getFixedPrice() * varifyCart.getQuantity();
    }

    if (cartTotalPrice != productTotalPrice) {
      log.warn("조작된 데이터의 접근이 감지됐습니다.. RequestVarifyCart: {}", requestVarifyCart);
      throw new ApiException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }
}
