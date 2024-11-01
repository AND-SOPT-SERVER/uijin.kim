package org.sopt.week3.dto.request;

import org.sopt.week3.enums.entity.Category;

public record DiaryInformationRequest(
        String title,
        String content,
        Category category
) {
}
