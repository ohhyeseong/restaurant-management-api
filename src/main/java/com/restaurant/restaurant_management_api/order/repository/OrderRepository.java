package com.restaurant.restaurant_management_api.order.repository;

import com.restaurant.restaurant_management_api.order.domain.OrderItem;
import com.restaurant.restaurant_management_api.order.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
