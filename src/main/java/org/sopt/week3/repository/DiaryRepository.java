package org.sopt.week3.repository;

import org.sopt.week3.enums.entity.DiaryCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;

//인터페이스인 이유 : save, findAll과 같은 메소드만 정의, 이런 구현체는 JpaRepository, CRUDRepository에서 정의
@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.content) DESC, d.updateAt DESC")
    List<DiaryEntity> findTop10ByOrderByContentLengthAndUpdateAtDesc();

    @Query("SELECT d FROM DiaryEntity d WHERE d.diaryCategory = :diaryCategory ORDER BY LENGTH(d.content) DESC, d.updateAt DESC")
    List<DiaryEntity> findTop10ByDiaryCategoryOrderByContentLengthAndUpdateAtDesc(@Param("diaryCategory") DiaryCategory diaryCategory);

    DiaryEntity findFirstByOrderByCreateAtDesc();
}
