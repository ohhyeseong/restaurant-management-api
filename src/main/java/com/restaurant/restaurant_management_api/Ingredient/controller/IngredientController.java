package com.restaurant.restaurant_management_api.Ingredient.controller;

import com.restaurant.restaurant_management_api.Ingredient.dto.IngredientRequest;
import com.restaurant.restaurant_management_api.Ingredient.service.IngredientService;
import com.restaurant.restaurant_management_api.global.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createIngredient(
            @RequestParam Long storeId,
            @Valid @RequestBody IngredientRequest request) {

        Long id = ingredientService.createIngredient(storeId, request);

        return ResponseEntity.ok(ApiResponse.success("식자재 등록에 성공하였습니다.", id));
    }
}