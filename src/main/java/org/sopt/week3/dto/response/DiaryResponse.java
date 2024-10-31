package org.sopt.week3.dto.response;

public record DiaryResponse(
        long id,
        String title
) {

    public static DiaryResponse of(final long id, final String title) {
        return new DiaryResponse(id, title);
    }
}
