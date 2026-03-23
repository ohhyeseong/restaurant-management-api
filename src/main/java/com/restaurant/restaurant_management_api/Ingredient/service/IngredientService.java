package com.restaurant.restaurant_management_api.Ingredient.service;

import com.restaurant.restaurant_management_api.Ingredient.domain.Ingredient;
import com.restaurant.restaurant_management_api.Ingredient.dto.IngredientRequest;
import com.restaurant.restaurant_management_api.Ingredient.repository.IngredientRepository;
import com.restaurant.restaurant_management_api.global.error.BusinessException;
import com.restaurant.restaurant_management_api.global.error.ErrorCode;
import com.restaurant.restaurant_management_api.store.domain.Store;
import com.restaurant.restaurant_management_api.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientService {

    private final IngredientRepository ingredientRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public Long createIngredient(Long storeId, IngredientRequest request) {

        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        Ingredient ingredient = Ingredient.builder()
                .store(store)
                .name(request.name())
                .stockQuantity(request.stockQuantity())
                .unit(request.unit())
                .build();

        return ingredientRepository.save(ingredient).getId();
    }
}