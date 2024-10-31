package org.sopt.week2.enums.entity;

public enum DiaryCategory {

    ALL("전체"),
    FOOD("음식"),
    EXERCISE("운동"),
    SECRET("비밀");

    private final String diaryCategory;

    DiaryCategory(final String diaryCategory) {
        this.diaryCategory = diaryCategory;
    }

    public String getDiaryCategory() {
        return diaryCategory;
    }
}
