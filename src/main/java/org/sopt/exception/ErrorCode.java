package org.sopt.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    POST_NOT_FOUND("POST_001", "게시글을 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    POST_INVALID_TITLE("POST_002", "제목은 비어있을 수 없습니다.", HttpStatus.BAD_REQUEST),
    POST_TITLE_TOO_LONG("POST_003", "제목은 50자를 초과할 수 없습니다.", HttpStatus.BAD_REQUEST),
    INVALID_INPUT("COMMON_001", "잘못된 입력값입니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("COMMON_002", "서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_TYPE("COMMON_003", "요청 파라미터의 타입이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("USER_001","사용자를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

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