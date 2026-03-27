package com.restaurant.restaurant_management_api.menu.service;

import com.restaurant.restaurant_management_api.Ingredient.domain.Ingredient;
import com.restaurant.restaurant_management_api.Ingredient.repository.IngredientRepository;
import com.restaurant.restaurant_management_api.global.error.BusinessException;
import com.restaurant.restaurant_management_api.global.error.ErrorCode;
import com.restaurant.restaurant_management_api.global.infra.S3Service;
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
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {

    private final MenuRepository menuRepository;
    private final IngredientRepository ingredientRepository;
    private final StoreRepository storeRepository;
    private final S3Service s3Service;

    @Transactional
    public Long createMenu(Long storeId, MenuCreateRequest request, MultipartFile image) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        String imageUrl = null;
        if (image != null && !image.isEmpty()) {
            imageUrl = s3Service.uploadFile(image);
        }

        Menu menu = Menu.builder()
                .store(store)
                .name(request.name())
                .price(request.price())
                .description(request.description())
                .category(request.category())
                .imageUrl(imageUrl)
                .build();

        for (MenuCreateRequest.RecipeRequest recipeDto : request.recipes()) {
            Ingredient ingredient = ingredientRepository.findById(recipeDto.ingredientId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.INGREDIENT_NOT_FOUND));

            Recipe recipe = Recipe.builder()
                    .menu(menu)
                    .ingredient(ingredient)
                    .requiredQuantity(recipeDto.requiredQuantity())
                    .build();

            menu.addRecipe(recipe);
        }

        return menuRepository.save(menu).getId();
    }
}