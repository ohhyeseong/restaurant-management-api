package com.restaurant.restaurant_management_api.menu.repository;

import com.restaurant.restaurant_management_api.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
