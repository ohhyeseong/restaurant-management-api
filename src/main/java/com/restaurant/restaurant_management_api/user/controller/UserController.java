package com.restaurant.restaurant_management_api.user.controller;

import com.restaurant.restaurant_management_api.global.response.ApiResponse;
import com.restaurant.restaurant_management_api.user.domain.CustomUserDetails;
import com.restaurant.restaurant_management_api.user.dto.UserLoginRequest;
import com.restaurant.restaurant_management_api.user.dto.UserSignupRequest;
import com.restaurant.restaurant_management_api.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<Long> signup(@Valid @RequestBody UserSignupRequest request) {
        Long userId = userService.signup(request);
        return ApiResponse.success(userId);
    }

    @PostMapping("/owner-signup")
    public ApiResponse<Long> ownerSignup(@Valid @RequestBody UserSignupRequest request) {
        return ApiResponse.success(userService.ownerSignup(request));
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@Valid @RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        return ApiResponse.success(token);
    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(
            HttpServletRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.substring(7);
            userService.logout(token, userDetails.getUsername());
        }

        return ApiResponse.success(null);
    }
}
