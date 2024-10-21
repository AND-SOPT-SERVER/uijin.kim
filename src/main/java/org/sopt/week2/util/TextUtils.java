package org.sopt.week2.util;

import org.sopt.week2.enums.response.ErrorMessage;
import org.sopt.week2.exception.BadRequestException;

public class TextUtils {

    private static final int DIARY_MAX_LENGTH = 30;

    public static void validateDiaryContent(String diaryContent) {

        if (diaryContent.length() > DIARY_MAX_LENGTH) {
            throw new BadRequestException(ErrorMessage.INPUT_LIMIT_LENGTH_OVER);
        }
    }
}
