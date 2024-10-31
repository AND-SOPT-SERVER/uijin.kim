package org.sopt.week2.repository;

import jakarta.persistence.*;
import org.sopt.week2.enums.entity.DiaryCategory;

import java.time.LocalDateTime;

@Entity
public class DiaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createAt;

    @Column(nullable = false)
    private LocalDateTime updateAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaryCategory diaryCategory;

    public DiaryEntity() {

    }

    public DiaryEntity(final String title, final String content, final DiaryCategory diaryCategory) {
        this.title = title;
        this.content = content;
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.diaryCategory = diaryCategory;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void updateDiary(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

}
