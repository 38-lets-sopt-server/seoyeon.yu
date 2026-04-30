package org.sopt.exception;

public class UserNotFoundException extends BaseException {
    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }

    public UserNotFoundException(Long id) {
        super(ErrorCode.USER_NOT_FOUND);
    }
}