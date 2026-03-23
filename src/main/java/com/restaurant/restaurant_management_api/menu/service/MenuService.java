package com.restaurant.restaurant_management_api.menu.service;

import com.restaurant.restaurant_management_api.Ingredient.domain.Ingredient;
import com.restaurant.restaurant_management_api.Ingredient.repository.IngredientRepository;
import com.restaurant.restaurant_management_api.global.error.BusinessException;
import com.restaurant.restaurant_management_api.global.error.ErrorCode;
import com.restaurant.restaurant_management_api.menu.domain.Menu;
import com.restaurant.restaurant_management_api.menu.dto.MenuCreateRequest;
import com.restaurant.restaurant_management_api.menu.repository.MenuRepository;
import com.restaurant.restaurant_management_api.recipe.domain.Recipe;
import com.restaurant.restaurant_management_api.recipe.repository.RecipeRepository;
import com.restaurant.restaurant_management_api.store.domain.Store;
import com.restaurant.restaurant_management_api.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Long createMenu(Long storeId, MenuCreateRequest request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        Menu menu = Menu.builder()
                .store(store)
                .name(request.name())
                .price(request.price())
                .isSoldOut(false)
                .build();
        Menu savedMenu = menuRepository.save(menu);

        // 2. 레시피(식자재 연결) 생성
        for (MenuCreateRequest.RecipeRequest recipeDto : request.recipes()) {
            Ingredient ingredient = ingredientRepository.findById(recipeDto.ingredientId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR)); // 식자재 없음 에러

            Recipe recipe = Recipe.builder()
                    .menu(savedMenu)
                    .ingredient(ingredient)
                    .requiredQuantity(recipeDto.requiredQuantity())
                    .build();
            recipeRepository.save(recipe);
        }

        return savedMenu.getId();
    }
}