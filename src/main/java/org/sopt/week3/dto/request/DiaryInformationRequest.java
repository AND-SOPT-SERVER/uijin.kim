package org.sopt.week3.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.sopt.week3.enums.entity.Category;

public record DiaryInformationRequest(
        @Size(min = 1, max = 10)
        @NotBlank
        String title,
        @Size(min = 1, max = 30)
        @NotBlank
        String content,

        Category category,

        boolean isVisible
) {
}
