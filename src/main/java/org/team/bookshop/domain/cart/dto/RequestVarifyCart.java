package org.team.bookshop.domain.cart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
@Builder
public class RequestVarifyCart implements Comparable<RequestVarifyCart> {

  private Long productId;
  private int quantity;
  private int price;

  @Override
  public int compareTo(@NotNull RequestVarifyCart other) {
    return this.productId.compareTo(other.productId);
  }
}
