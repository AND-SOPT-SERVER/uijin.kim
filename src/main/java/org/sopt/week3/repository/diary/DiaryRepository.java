package org.sopt.week3.repository.diary;

import org.sopt.week3.enums.entity.Category;
import org.sopt.week3.repository.user.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//인터페이스인 이유 : save, findAll과 같은 메소드만 정의, 이런 구현체는 JpaRepository, CRUDRepository에서 정의
@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    @Query("""
            SELECT d FROM DiaryEntity d 
            ORDER BY 
                CASE WHEN :criteria = 'contentLength' THEN LENGTH(d.content) END DESC,
                CASE WHEN :criteria = 'date' THEN d.date END DESC
            """)
    List<DiaryEntity> findAll(final Pageable pageable, @Param("criteria") final String criteria);

    @Query("""
            SELECT d FROM DiaryEntity d 
            WHERE (d.category = :category)
            ORDER BY 
                CASE WHEN :criteria = 'contentLength' THEN LENGTH(d.content) END DESC,
                CASE WHEN :criteria = 'date' THEN d.date END DESC
            """)
    List<DiaryEntity> findAllByCategory(@Param("category") final Category category, final Pageable pageable, @Param("criteria") final String criteria);

    DiaryEntity findFirstByUserEntityOrderByDateDesc(final UserEntity userEntity);

    DiaryEntity findByUserEntityAndId(UserEntity userEntity, Long id);

    void deleteByUserEntityAndId(UserEntity userEntity, Long id);
}
