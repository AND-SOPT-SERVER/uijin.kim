package org.sopt.week3.repository.diary;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.sopt.week3.enums.entity.Category;
import org.sopt.week3.repository.BaseTimeEntity;
import org.sopt.week3.repository.user.UserEntity;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "diary")
public class DiaryEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "bigint", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @Column(name = "title", columnDefinition = "varchar(255)", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "text")
    private String content;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "is_visible", columnDefinition = "tinyint", nullable = false)
    private boolean isVisible;

    @Builder
    public DiaryEntity(UserEntity userEntity, String title, String content, Category category, boolean isVisible) {
        this.userEntity = userEntity;
        this.title = title;
        this.content = content;
        this.category = category;
        this.isVisible = isVisible;
    }

    public void updateDiary(final String title, final String content) {
        this.title = title;
        this.content = content;
    }

}
