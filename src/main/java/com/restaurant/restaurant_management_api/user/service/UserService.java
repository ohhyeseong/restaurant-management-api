package com.restaurant.restaurant_management_api.user.service;

import com.restaurant.restaurant_management_api.global.config.JwtTokenProvider;
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
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
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
            throw new IllegalArgumentException("이미 존재하는 이메일 입니다.");
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
        // 1. 이메일 확인
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));

        // 2. 비밀번호 일치 확인 (암호화된 비번과 비교)
        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        // 3. 토큰 생성 및 반환
        return jwtTokenProvider.createToken(user.getEmail(), user.getRole().name());
    }
}
