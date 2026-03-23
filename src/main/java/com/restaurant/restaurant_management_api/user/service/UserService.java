package com.restaurant.restaurant_management_api.user.service;

import com.restaurant.restaurant_management_api.global.config.JwtTokenProvider;
import com.restaurant.restaurant_management_api.global.error.BusinessException;
import com.restaurant.restaurant_management_api.global.error.ErrorCode;
import com.restaurant.restaurant_management_api.user.domain.User;
import com.restaurant.restaurant_management_api.user.domain.UserRole;
import com.restaurant.restaurant_management_api.user.dto.UserLoginRequest;
import com.restaurant.restaurant_management_api.user.dto.UserSignupRequest;
import com.restaurant.restaurant_management_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public Long signup(UserSignupRequest request){
        if(userRepository.existsByEmail(request.email())){
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(request.password());


        User user = User.builder()
                .email(request.email())
                .password(encodedPassword)
                .name(request.name())
                .role(UserRole.CUSTOMER)
                .build();

        return userRepository.save(user).getId();
    }

    @Transactional
    public Long ownerSignup(UserSignupRequest request) {
        if(userRepository.existsByEmail(request.email())){
            throw new BusinessException(ErrorCode.DUPLICATE_EMAIL);
        }

        User user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .name(request.name())
                .role(UserRole.OWNER)
                .build();

        return userRepository.save(user).getId();
    }

    @Transactional
    public String login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_EMAIL));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_PASSWORD);
        }

        return jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
    }
}
