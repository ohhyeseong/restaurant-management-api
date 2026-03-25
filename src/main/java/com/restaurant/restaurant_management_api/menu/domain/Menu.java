package com.restaurant.restaurant_management_api.menu.domain;

import com.restaurant.restaurant_management_api.recipe.domain.Recipe;
import com.restaurant.restaurant_management_api.store.domain.Store;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Boolean isSoldOut = false;

    @OneToMany(mappedBy = "menu")
    private List<Recipe> recipes = new ArrayList<>();

    @Builder
    public Menu(Store store, String name, Integer price, Boolean isSoldOut, List<Recipe> recipes) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.isSoldOut = isSoldOut;
        this.recipes =recipes;
    }
}
