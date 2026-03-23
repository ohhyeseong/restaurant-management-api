package com.restaurant.restaurant_management_api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
        @NotBlank(message = "필수 입력란입니다.")
        @Email(message = "이메일을 입력해주세요!")
        String email,

        @NotBlank(message = "필수 입력란입니다.")
        String password
) {
}
