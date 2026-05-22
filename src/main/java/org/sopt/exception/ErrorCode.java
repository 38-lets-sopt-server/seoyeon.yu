package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    COMMON_INVALID_INPUT("COMMON_001", "잘못된 입력값입니다.", HttpStatus.BAD_REQUEST),
    COMMON_INTERNAL_SERVER_ERROR("COMMON_002", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    COMMON_INVALID_TYPE("COMMON_003", "요청 파라미터의 타입이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),

    POST_NOT_FOUND("POST_001", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    USER_NOT_FOUND("USER_001", "사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    USER_EMAIL_DUPLICATE("USER_002", "이미 사용 중인 이메일입니다.", HttpStatus.CONFLICT),

    AUTH_UNAUTHORIZED("AUTH_001", "인증이 필요합니다.", HttpStatus.UNAUTHORIZED),
    AUTH_FORBIDDEN("AUTH_002", "접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    AUTH_INVALID_CREDENTIALS("AUTH_003", "이메일 또는 비밀번호가 올바르지 않습니다.", HttpStatus.UNAUTHORIZED);


    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getCode() { return code; }
    public String getMessage() { return message; }
    public HttpStatus getHttpStatus() { return httpStatus; }
    public String toMessage() { return "[" + code + "] " + message; }
}