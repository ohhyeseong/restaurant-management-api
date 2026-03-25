package com.restaurant.restaurant_management_api.order.controller;

import com.restaurant.restaurant_management_api.global.common.ApiResponse;
import com.restaurant.restaurant_management_api.order.dto.OrderRequest;
import com.restaurant.restaurant_management_api.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createOrder(@Valid @RequestBody OrderRequest request) {
        Long orderId = orderService.createOrder(request);
        return ResponseEntity.ok(ApiResponse.success("주문이 완료되었습니다. 재고가 차감되었습니다.", orderId));
    }
}