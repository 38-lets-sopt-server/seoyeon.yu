package org.sopt.exception;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }

    public UserNotFoundException(Long id) {
        super(ErrorCode.POST_NOT_FOUND);
    }
}