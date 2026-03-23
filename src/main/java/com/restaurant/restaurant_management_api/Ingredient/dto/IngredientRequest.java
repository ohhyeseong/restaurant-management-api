package com.restaurant.restaurant_management_api.Ingredient.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IngredientRequest(
        @NotBlank(message = "식자재 이름은 필수입니다.")
        String name,

        @NotNull(message = "초기 재고량을 입력해주세요.")
        @Min(value = 0, message = "재고는 0보다 작을 수 없습니다.")
        Integer stockQuantity,

        @NotBlank(message = "단위(g, 개, 인분 등)를 입력해주세요.")
        String unit
) {
}