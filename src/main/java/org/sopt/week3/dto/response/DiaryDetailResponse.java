package org.sopt.week3.dto.response;

import org.sopt.week3.enums.entity.Category;

import java.time.LocalDateTime;

public record DiaryDetailResponse(
        long id,
        String title,
        String content,
        Category category,
        LocalDateTime date
) {

    public static DiaryDetailResponse of(final long id, final String title, final String content, final Category category, final LocalDateTime date) {
        return new DiaryDetailResponse(id, title, content, category, date);
    }
}
