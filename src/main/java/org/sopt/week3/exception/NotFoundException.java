package org.sopt.week3.exception;

import org.sopt.week3.enums.response.ErrorMessage;

public class NotFoundException extends DiaryException {

    public NotFoundException(final ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
