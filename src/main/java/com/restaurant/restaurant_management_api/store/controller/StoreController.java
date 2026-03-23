package com.restaurant.restaurant_management_api.store.controller;

import com.restaurant.restaurant_management_api.store.dto.StoreCreateRequest;
import com.restaurant.restaurant_management_api.store.service.StoreService;
import com.restaurant.restaurant_management_api.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/create")
    public ResponseEntity<Long> createStore(
            @AuthenticationPrincipal CustomUserDetails userDetails, // ★ 시큐리티가 로그인한 유저를 주입해줘!
            @RequestBody StoreCreateRequest request) {

        Long user = userDetails.getUser().getId();
        // userDetails 안에 우리가 아까 넣어둔 User 엔티티가 들어있어.
        return ResponseEntity.ok(storeService.createStore(user, request));
    }
}
