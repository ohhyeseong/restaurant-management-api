package com.restaurant.restaurant_management_api.menu.controller;

import com.restaurant.restaurant_management_api.global.common.ApiResponse;
import com.restaurant.restaurant_management_api.menu.dto.MenuCreateRequest;
import com.restaurant.restaurant_management_api.menu.service.MenuService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/stores/{storeId}/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ApiResponse<Long> createMenu(
            @PathVariable Long storeId,
            @RequestPart("request") @Valid MenuCreateRequest request,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        return ApiResponse.success(menuService.createMenu(storeId, request, image));
    }
}
