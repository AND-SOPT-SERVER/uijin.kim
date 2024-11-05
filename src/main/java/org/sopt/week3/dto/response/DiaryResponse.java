package org.sopt.week3.dto.response;

import java.time.LocalDateTime;

public record DiaryResponse(
        long diaryId,
        long userId,
        String title,
        String nickname,
        LocalDateTime date
) {

    public static DiaryResponse of(final long diaryId, final long userId, final String title, final String nickname, final LocalDateTime date) {
        return new DiaryResponse(diaryId, userId, title, nickname, date);
    }
}
