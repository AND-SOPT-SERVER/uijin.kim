package org.sopt.week3.dto.response;

import java.time.LocalDateTime;

public record DiaryResponse(
        long diaryId,
        String title,
        String nickname,
        LocalDateTime date
) {

    public static DiaryResponse of(final long diaryId, final String title, final String nickname, final LocalDateTime date) {
        return new DiaryResponse(diaryId, title, nickname, date);
    }
}
