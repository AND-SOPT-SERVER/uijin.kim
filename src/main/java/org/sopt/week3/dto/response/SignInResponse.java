package org.sopt.week3.dto.response;

public record SignInResponse(
        long userId,
        String nickname
) {
    public static SignInResponse of(final long userId, final String nickname) {
        return new SignInResponse(userId, nickname);
    }

}
