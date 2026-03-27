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

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MenuCategory category;

    @Column(nullable = false)
    private Boolean isSoldOut = false;

    private String imageUrl;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL) // 메뉴 삭제 시 레시피도 삭제되도록 cascade 설정 권장
    private List<Recipe> recipes = new ArrayList<>();

    @Builder
    public Menu(Store store, String name, Integer price, String description,
                MenuCategory category, Boolean isSoldOut, String imageUrl) {
        this.store = store;
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.isSoldOut = isSoldOut != null ? isSoldOut : false;
        this.imageUrl = imageUrl;
    }

    public void changeSoldOutStatus(boolean status) {
        this.isSoldOut = status;
    }

    public void update(String name, Integer price, String description, MenuCategory category, String imageUrl) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.category = category;
        this.imageUrl = imageUrl;
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }
}
