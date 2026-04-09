package org.sopt.dto.response;

public class PostResponse {
    public final Long id;
    public final String title;
    public final String content;
    public final String author;
    public final String createdAt;

    public PostResponse(Long id, String title, String content, String author, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " - " + author + " (" + createdAt + ")\n" + content;
    }
}