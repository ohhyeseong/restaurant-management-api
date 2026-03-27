package com.restaurant.restaurant_management_api.store.service;

import com.restaurant.restaurant_management_api.global.error.BusinessException;
import com.restaurant.restaurant_management_api.global.error.ErrorCode;
import com.restaurant.restaurant_management_api.store.domain.Store;
import com.restaurant.restaurant_management_api.store.dto.StoreCreateRequest;
import com.restaurant.restaurant_management_api.store.dto.StoreResponse;
import com.restaurant.restaurant_management_api.store.dto.StoreUpdateRequest;
import com.restaurant.restaurant_management_api.store.repository.StoreRepository;
import com.restaurant.restaurant_management_api.user.domain.User;
import com.restaurant.restaurant_management_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<StoreResponse> getAllStores(Long ownerId) {
        return storeRepository.findAllByOwnerId(ownerId).stream()
                .map(StoreResponse::from).collect(Collectors.toList());
    }

    @Transactional
    public void updateStore(Long ownerId, Long storeId, StoreUpdateRequest request) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        if (!store.getOwner().getId().equals(ownerId)) {
            throw new BusinessException(ErrorCode.NOT_OWNER);
        }

        store.update(
                request.name(),
                request.address(),
                request.openTime(),
                request.closeTime()
        );
    }

    @Transactional
    public void deleteStore(Long ownerId, Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STORE_NOT_FOUND));

        if (!store.getOwner().getId().equals(ownerId)) {
            throw new BusinessException(ErrorCode.NOT_OWNER);
        }

        storeRepository.delete(store);
    }
}
