package com.restaurant.restaurant_management_api.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserSignupRequest(

        @Email(message = "이메일 형식으로 작성해주세요!")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력란입니다.")
        String password,

        @NotBlank(message = "이름은 필수 입력란입니다.")
        String name
) {
}
