package org.sopt.validator;

public class PostValidator {

    public void validateTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목은 필수입니다!");
        }
        if (title.length() > 50) {
            throw new IllegalArgumentException("제목은 최대 50자까지 가능합니다!");
        }
    }
}