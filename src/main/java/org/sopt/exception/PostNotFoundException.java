package org.sopt.exception;

public class PostNotFoundException extends BaseException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }

    public PostNotFoundException(Long id) {
        super(ErrorCode.POST_NOT_FOUND);
    }
}