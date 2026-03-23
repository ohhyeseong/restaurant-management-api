package com.restaurant.restaurant_management_api.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record StoreCreateRequest(
        @NotBlank(message = "필수 입력란입니다.")
        String name,

        @NotBlank(message = "필수 입력란입니다.")
        String address,

        @NotNull(message = "오픈 시간을 입력해주세요")
        LocalTime openTime,

        @NotNull(message = "마감 시간을 입력해주세요")
        LocalTime closeTime
) {
}
