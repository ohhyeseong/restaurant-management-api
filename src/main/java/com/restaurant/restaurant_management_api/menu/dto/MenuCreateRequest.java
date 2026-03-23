package com.restaurant.restaurant_management_api.menu.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record MenuCreateRequest(
        @NotBlank(message = "메뉴 이름은 필수입니다.")
        String name,

        @NotNull(message = "가격은 필수입니다.")
        @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
        Integer price,

        @NotNull(message = "레시피 정보는 필수입니다.")
        List<RecipeRequest> recipes
) {
    public record RecipeRequest(
            @NotNull(message = "식자재 ID는 필수입니다.")
            Long ingredientId,

            @NotNull(message = "필요 수량은 필수입니다.")
            @Min(value = 1, message = "수량은 1 이상이어야 합니다.")
            Integer requiredQuantity
    ) {}
}