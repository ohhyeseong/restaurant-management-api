package com.restaurant.restaurant_management_api.store.repository;

import com.restaurant.restaurant_management_api.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Long> {

}
