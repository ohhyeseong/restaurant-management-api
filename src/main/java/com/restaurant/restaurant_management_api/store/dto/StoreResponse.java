package com.restaurant.restaurant_management_api.store.dto;

import com.restaurant.restaurant_management_api.store.domain.Store;

import java.time.LocalTime;

public record StoreResponse(
        Long id,
        String name,
        String address,
        LocalTime openTime,
        LocalTime closeTime
) {
    public static StoreResponse from(Store store) {
        return new StoreResponse(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getOpenTime(),
                store.getCloseTime()
        );
    }
}
