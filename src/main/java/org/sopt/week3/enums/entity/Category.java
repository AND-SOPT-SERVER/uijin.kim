package org.sopt.week3.enums.entity;

public enum Category {

    ALL("전체"),
    FOOD("음식"),
    EXERCISE("운동"),
    SECRET("비밀");

    private final String diaryCategory;

    Category(final String diaryCategory) {
        this.diaryCategory = diaryCategory;
    }

    public String getDiaryCategory() {
        return diaryCategory;
    }
}
