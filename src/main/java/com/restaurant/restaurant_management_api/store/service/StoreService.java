package com.restaurant.restaurant_management_api.store.service;

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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        if (!owner.getRole().name().equals("OWNER")) {
            throw new IllegalStateException("점주 권한이 있는 사용자만 매장을 등록할 수 있습니다.");
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
