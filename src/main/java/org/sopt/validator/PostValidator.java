package org.sopt.validator;

public class PostValidator {

    private static final int MAX_TITLE_LENGTH = 50;

    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다!");
        }
        if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("제목은 최대 " + MAX_TITLE_LENGTH + "자까지 가능합니다!");
        }
    }
}