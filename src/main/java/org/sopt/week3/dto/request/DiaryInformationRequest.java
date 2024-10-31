package org.sopt.week3.dto.request;

import org.sopt.week3.enums.entity.DiaryCategory;

public record DiaryInformationRequest(
        String title,
        String content,
        DiaryCategory diaryCategory
) {
}
