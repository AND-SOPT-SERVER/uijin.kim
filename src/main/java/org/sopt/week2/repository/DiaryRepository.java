package org.sopt.week2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

//인터페이스인 이유 : save, findAll과 같은 메소드만 정의, 이런 구현체는 JpaRepository, CRUDRepository에서 정의
@Component
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    List<DiaryEntity> findTop10ByOrderByUpdateAtDesc();
}
