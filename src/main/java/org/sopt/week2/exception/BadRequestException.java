package org.sopt.week2.exception;

import org.sopt.week2.enums.response.ErrorMessage;

public class BadRequestException extends DiaryException {

    public BadRequestException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
