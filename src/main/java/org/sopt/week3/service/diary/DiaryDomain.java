package org.sopt.week3.service.diary;

import org.sopt.week3.enums.entity.Category;
import org.sopt.week3.repository.user.UserEntity;

import java.time.LocalDateTime;

public record DiaryDomain(
        long id, UserEntity userEntity, String title, String content, Category category, boolean isVisible,
        LocalDateTime date
) {

    public static DiaryDomain of(final long id, final UserEntity userEntity, final String title, final String content, final Category category, final boolean isVisible, final LocalDateTime date) {
        return new DiaryDomain(id, userEntity, title, content, category, isVisible, date);
    }

}
