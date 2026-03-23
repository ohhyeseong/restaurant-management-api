package com.restaurant.restaurant_management_api.store.service;

import com.restaurant.restaurant_management_api.global.error.BusinessException;
import com.restaurant.restaurant_management_api.global.error.ErrorCode;
import com.restaurant.restaurant_management_api.store.domain.Store;
import com.restaurant.restaurant_management_api.store.dto.StoreCreateRequest;
import com.restaurant.restaurant_management_api.store.repository.StoreRepository;
import com.restaurant.restaurant_management_api.user.domain.User;
import com.restaurant.restaurant_management_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createStore(Long ownerId, StoreCreateRequest request) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!owner.getRole().name().equals("OWNER")) {
            throw new BusinessException(ErrorCode.NOT_OWNER);
        }

        Store store = Store.builder()
                .owner(owner)
                .name(request.name())
                .address(request.address())
                .openTime(request.openTime())
                .closeTime(request.closeTime())
                .build();

        return storeRepository.save(store).getId();
    }
}
