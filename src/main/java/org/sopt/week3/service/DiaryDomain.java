package org.sopt.week3.service;

import org.sopt.week3.enums.response.ErrorMessage;
import org.sopt.week3.exception.BadRequestException;

import java.time.LocalDateTime;

public record DiaryDomain(
        long id, String title, String content, LocalDateTime createAt, LocalDateTime updateAt
) {

    public static DiaryDomain of(final long id, final String title, final String content, final LocalDateTime createAt, final LocalDateTime updateAt) {
        return new DiaryDomain(id, title, content, createAt, updateAt);
    }

    public void checkDuplicatedTitle(final String title) {
        if (!this.title.equals(title)) {
            throw new BadRequestException(ErrorMessage.INPUT_IN_LIMIT_TIME);
        }
    }
}
