package org.team.bookshop.domain.order.dto.request;

import lombok.Data;
import org.team.bookshop.domain.order.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderCreateRequest {

    private String merchantUid;
    private Long userId;
    private List<OrderItemRequest> orderItemRequests;

    public OrderCreateRequest() {
    }

    public OrderCreateRequest(String merchantUid, List<OrderItemRequest> orderItemRequests) {
        this.merchantUid = merchantUid;
        this.orderItemRequests = orderItemRequests;
    }


    public List<OrderItem> toOrderItems() {
        return orderItemRequests.stream().map(oir -> oir.toOrderItemEntity()).collect(Collectors.toList());
    }

    public List<Long> toProductIds() {
        return orderItemRequests.stream().map(oir -> oir.getProductId()).collect(Collectors.toList());
    }
}
