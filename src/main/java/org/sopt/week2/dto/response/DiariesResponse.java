package org.sopt.week2.dto.response;

import java.util.List;

public record DiariesResponse(
        List<DiaryResponse> diaries
) {

    public static DiariesResponse of(final List<DiaryResponse> diaries) {
        return new DiariesResponse(diaries);
    }
}
