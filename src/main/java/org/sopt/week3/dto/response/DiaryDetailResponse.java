package org.sopt.week3.dto.response;

import java.time.LocalDateTime;

public record DiaryDetailResponse(
        long id,
        String title,
        String content,
        LocalDateTime createAt
) {

    public static DiaryDetailResponse of(final long id, final String title, final String content, final LocalDateTime createAt) {
        return new DiaryDetailResponse(id, title, content, createAt);
    }
}
