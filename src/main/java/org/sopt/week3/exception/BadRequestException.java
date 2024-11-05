package org.sopt.week3.exception;

import org.sopt.week3.enums.response.ErrorMessage;

public class BadRequestException extends DiaryException {

    public BadRequestException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
