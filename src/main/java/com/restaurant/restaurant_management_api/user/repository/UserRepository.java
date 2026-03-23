package com.restaurant.restaurant_management_api.user.repository;

import com.restaurant.restaurant_management_api.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
}
