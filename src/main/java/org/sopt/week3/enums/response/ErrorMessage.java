package org.sopt.week3.enums.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorMessage {

    // BAD_REQUEST : 400
    INPUT_LIMIT_LENGTH_OVER("제한 글자를 초과했습니다.", HttpStatus.BAD_REQUEST),
    INPUT_IN_LIMIT_TIME("일정 시간 이후에 일기를 작성할 수 있습니다.", HttpStatus.BAD_REQUEST),
    INPUT_DUPLICATED_TITLE("중복된 제목입니다.", HttpStatus.BAD_REQUEST),
    INPUT_DUPLICATED_USERNAME("중복된 이름이 존재합니다.", HttpStatus.BAD_REQUEST),

    // NOT_FOUND : 404
    NOT_FOUND_DIARY("해당 일기를 찾을 수 없습니다.", HttpStatus.NOT_FOUND);

    private final String errorMessage;
    private final HttpStatus errorStatus;

    ErrorMessage(final String errorMessage, final HttpStatus errorStatus) {
        this.errorMessage = errorMessage;
        this.errorStatus = errorStatus;
    }

}
