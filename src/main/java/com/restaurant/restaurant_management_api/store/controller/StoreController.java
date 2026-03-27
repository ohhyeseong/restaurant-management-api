package com.restaurant.restaurant_management_api.store.controller;

import com.restaurant.restaurant_management_api.global.response.ApiResponse;
import com.restaurant.restaurant_management_api.store.dto.StoreCreateRequest;
import com.restaurant.restaurant_management_api.store.dto.StoreResponse;
import com.restaurant.restaurant_management_api.store.service.StoreService;
import com.restaurant.restaurant_management_api.user.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return ResponseEntity.ok(storeService.createStore(user, request));
    }

    @GetMapping
    public ApiResponse<List<StoreResponse>> getAllStores(
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {
        Long ownerId = userDetails.getUser().getId();

        List<StoreResponse> stores = storeService.getAllStores(ownerId);

        return ApiResponse.success(stores);
    }
}
