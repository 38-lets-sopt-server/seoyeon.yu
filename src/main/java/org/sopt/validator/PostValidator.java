package org.sopt.validator;

import org.sopt.exception.BaseException;
import org.sopt.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
public class PostValidator {

    private static final int MAX_TITLE_LENGTH = 50;

    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new BaseException(ErrorCode.POST_INVALID_TITLE);
        }

        if (title.length() > MAX_TITLE_LENGTH) {
            throw new BaseException(ErrorCode.POST_TITLE_TOO_LONG);
        }
    }
}
