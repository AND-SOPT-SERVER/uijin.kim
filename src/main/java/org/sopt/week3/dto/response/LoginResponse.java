package org.sopt.week3.dto.response;

public record LoginResponse(
        long userId,
        String nickname
) {
    public static LoginResponse of(final long userId, final String nickname) {
        return new LoginResponse(userId, nickname);
    }

}
