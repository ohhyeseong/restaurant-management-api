package com.restaurant.restaurant_management_api.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // 유저 관련 (U)
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "U001", "이미 존재하는 이메일입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U002", "존재하지 않는 사용자입니다."),
    INVALID_EMAIL(HttpStatus.UNAUTHORIZED, "U003", "이메일이 일치하지 않습니다."),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "U003", "비밀번호가 일치하지 않습니다."),

    // 매장/메뉴 관련 (S)
    STORE_NOT_FOUND(HttpStatus.NOT_FOUND, "S001", "존재하지 않는 매장입니다."),
    NOT_OWNER(HttpStatus.FORBIDDEN, "S002", "점주 권한이 없습니다."),

    // 공통 (C)
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C001", "서버 내부 오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C002", "잘못된 입력값입니다.");

    private final HttpStatus status;
    private final String code;
    private final String message;
}