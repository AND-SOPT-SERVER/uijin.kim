package org.sopt.week3.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({BadRequestException.class, NotFoundException.class})
    public ResponseEntity<String> handleBadRequestException(final DiaryException diaryException) {
        return ResponseEntity.status(diaryException.getErrorStatus()).body(diaryException.getErrorMessage());
    }
}
