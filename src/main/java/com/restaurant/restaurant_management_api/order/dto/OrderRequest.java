package com.restaurant.restaurant_management_api.order.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(
        @NotNull(message = "주문 항목은 필수입니다.")
        List<OrderItemRequest> orderItems
) {
    public record OrderItemRequest(
            @NotNull(message = "메뉴 아이디는 필수 입니다.")
            Long menuId,

            @NotNull(message = "수량은 필수 입니다.")
            @Min(value = 1, message = "최소 1개 이상 주문해야 합니다.")
            Integer quantity
    ){}
}
