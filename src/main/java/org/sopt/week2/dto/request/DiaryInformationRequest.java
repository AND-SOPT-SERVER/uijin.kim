package org.sopt.week2.dto.request;

import org.sopt.week2.enums.entity.DiaryCategory;

public record DiaryInformationRequest(
        String title,
        String content,
        DiaryCategory diaryCategory
) {
}
