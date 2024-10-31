package org.sopt.week2.exception;

import org.sopt.week2.enums.response.ErrorMessage;

public class NotFoundException extends DiaryException {

    public NotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
