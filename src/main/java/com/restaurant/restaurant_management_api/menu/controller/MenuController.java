package com.restaurant.restaurant_management_api.menu.controller;

import com.restaurant.restaurant_management_api.global.response.ApiResponse;
import com.restaurant.restaurant_management_api.menu.dto.MenuCreateRequest;
import com.restaurant.restaurant_management_api.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createMenu(
            @RequestParam Long storeId,
            @Valid @RequestBody MenuCreateRequest request) {

        Long menuId = menuService.createMenu(storeId, request);
        return ResponseEntity.ok(ApiResponse.success("메뉴 등록에 성공하였습니다.", menuId));
    }
}
