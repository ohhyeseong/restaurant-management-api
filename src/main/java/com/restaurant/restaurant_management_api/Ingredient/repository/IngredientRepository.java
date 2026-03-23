package com.restaurant.restaurant_management_api.Ingredient.repository;

import com.restaurant.restaurant_management_api.Ingredient.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    List<Ingredient> findAllByStoreId(Long storeId);
}
