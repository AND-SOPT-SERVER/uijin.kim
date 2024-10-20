package org.sopt.week2.dto.response;

public record DiaryResponse(
        long id,
        String title
) {

    public static DiaryResponse of(final long id, final String title) {
        return new DiaryResponse(id, title);
    }
}
