package com.restaurant.restaurant_management_api.order.domain;

import com.restaurant.restaurant_management_api.menu.domain.Menu;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", nullable = false)
    private Menu menu;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Integer price;


    @Builder
    public OrderItem(Orders order, Menu menu, Integer quantity,Integer price){
        this.order = order;
        this.menu = menu;
        this.quantity = quantity;
        this.price = price;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }


}