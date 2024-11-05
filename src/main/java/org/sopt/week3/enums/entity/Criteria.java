package org.sopt.week3.enums.entity;

import lombok.Getter;

@Getter
public enum Criteria {

    RECENT_DATE("date"),
    CONTENT_LENGTH("contentLength");

    private final String criteria;

    Criteria(String criteria) {
        this.criteria = criteria;
    }
}
