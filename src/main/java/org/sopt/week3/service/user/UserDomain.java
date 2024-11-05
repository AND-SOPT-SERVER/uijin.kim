package org.sopt.week3.service.user;

import org.sopt.week3.enums.response.ErrorMessage;
import org.sopt.week3.exception.BadRequestException;

public record UserDomain(
        long id,
        String username,
        String password,
        String nickname
) {

    public static UserDomain of(final long id, final String username, final String password, final String nickname) {
        return new UserDomain(id, username, password, nickname);
    }

    public void checkDuplicatedUsername(final String username) {
        if (this.username.equals(username)) {
            throw new BadRequestException(ErrorMessage.INPUT_DUPLICATED_USERNAME);
        }
    }
}
