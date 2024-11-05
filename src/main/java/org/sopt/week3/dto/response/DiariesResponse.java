package org.sopt.week3.dto.response;

import java.util.List;

public record DiariesResponse(
        List<DiaryResponse> diaries
) {

    public static DiariesResponse of(final List<DiaryResponse> diaries) {
        return new DiariesResponse(diaries);
    }
}
