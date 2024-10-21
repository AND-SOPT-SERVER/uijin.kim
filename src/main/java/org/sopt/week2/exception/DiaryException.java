package org.sopt.week2.exception;

import org.sopt.week2.enums.response.ErrorMessage;
import org.springframework.http.HttpStatus;

public class DiaryException extends RuntimeException {

    private final String errorMessage;
    private final HttpStatus errorStatus;

    public DiaryException(final ErrorMessage errorMessage) {
        super(errorMessage.getErrorMessage());
        this.errorMessage = errorMessage.getErrorMessage();
        this.errorStatus = errorMessage.getErrorStatus();
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }
}
