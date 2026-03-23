package com.restaurant.restaurant_management_api.user.controller;

import com.restaurant.restaurant_management_api.user.dto.UserLoginRequest;
import com.restaurant.restaurant_management_api.user.dto.UserSignupRequest;
import com.restaurant.restaurant_management_api.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Long> signup(@Valid @RequestBody UserSignupRequest request) {
        Long userId = userService.signup(request);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/owner-signup")
    public ResponseEntity<Long> ownerSignup(@Valid @RequestBody UserSignupRequest request) {
        return ResponseEntity.ok(userService.ownerSignup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok(token);
    }
}
