package org.sopt.week2.util;

public class TextUtils {

    private static final int DIARY_MAX_LENGTH = 30;

    public static void validateDiaryContent(String diaryContent) {

        if (diaryContent.length() > DIARY_MAX_LENGTH) {
            throw new IllegalArgumentException("일기 글자수를 30자 이하로 제한해주세요.");
        }
    }
}
