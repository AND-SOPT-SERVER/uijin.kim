package org.sopt.week2.enums.response;

import org.springframework.http.HttpStatus;

public enum ErrorMessage {

    // BAD_REQUEST : 400
    INPUT_LIMIT_LENGTH_OVER("제한 글자를 초과했습니다.", HttpStatus.BAD_REQUEST),
    INPUT_IN_LIMIT_TIME("일정 시간 이후에 일기를 작성할 수 있습니다.", HttpStatus.BAD_REQUEST);

    private final String errorMessage;
    private final HttpStatus errorStatus;

    ErrorMessage(final String errorMessage, final HttpStatus errorStatus) {
        this.errorMessage = errorMessage;
        this.errorStatus = errorStatus;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getErrorStatus() {
        return errorStatus;
    }
}
