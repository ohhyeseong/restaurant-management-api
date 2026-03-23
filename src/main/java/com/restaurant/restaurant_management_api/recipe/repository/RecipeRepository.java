package com.restaurant.restaurant_management_api.recipe.repository;

import com.restaurant.restaurant_management_api.recipe.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
}
