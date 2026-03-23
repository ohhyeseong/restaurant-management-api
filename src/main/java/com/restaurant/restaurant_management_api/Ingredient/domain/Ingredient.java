package com.restaurant.restaurant_management_api.Ingredient.domain;

import com.restaurant.restaurant_management_api.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false)
    private String unit;

    @Builder
    public Ingredient(Store store, String name, Integer stockQuantity, String unit){
        this.store = store;
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.unit = unit;
    }
}
