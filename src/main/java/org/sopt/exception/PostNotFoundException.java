package org.sopt.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super(ErrorCode.POST_NOT_FOUND.toMessage());
    }
}