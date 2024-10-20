package org.sopt.week2.service;

import java.time.LocalDateTime;

public record DiaryDomain(
        long id, String title, String content, LocalDateTime createAt, LocalDateTime updateAt
) {

    public static DiaryDomain of(final long id, final String title, final String content, final LocalDateTime createAt, final LocalDateTime updateAt) {
        return new DiaryDomain(id, title, content, createAt, updateAt);
    }
}
