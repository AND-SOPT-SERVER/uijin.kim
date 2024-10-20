package org.sopt.week2.service;

import jakarta.persistence.EntityNotFoundException;
import org.sopt.week2.dto.response.DiaryDetailResponse;
import org.sopt.week2.dto.response.DiaryResponse;
import org.sopt.week2.repository.DiaryEntity;
import org.sopt.week2.repository.DiaryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(final DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public void createDiary(final String title, final String content) {

        diaryRepository.save(new DiaryEntity(title, content));
    }

    @Transactional(readOnly = true)
    public List<DiaryResponse> getDiaries() {

        final List<DiaryEntity> diaryEntities = diaryRepository.findTop10ByOrderByUpdateAtDesc();

        final List<DiaryDomain> diaryDomains = diaryEntities.stream()
                .map(diaryEntity -> new DiaryDomain(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getContent(), diaryEntity.getCreateAt(), diaryEntity.getUpdateAt()))
                .toList();

        return diaryDomains.stream()
                .map(diaryDomain -> DiaryResponse.of(diaryDomain.id(), diaryDomain.title()))
                .toList();
    }

    @Transactional(readOnly = true)
    public DiaryDetailResponse getDiary(final long diaryId) {

        final DiaryEntity findDiaryEntity = diaryRepository.findById(diaryId).orElseThrow(
                () -> new EntityNotFoundException("해당 일기를 찾을 수 없습니다.")
        );

        final DiaryDomain diaryDomain = DiaryDomain.of(findDiaryEntity.getId(), findDiaryEntity.getTitle(), findDiaryEntity.getContent(), findDiaryEntity.getCreateAt(), findDiaryEntity.getUpdateAt());

        return DiaryDetailResponse.of(diaryDomain.id(), diaryDomain.title(), diaryDomain.content(), diaryDomain.createAt());
    }

    @Transactional
    public void updateDiary(final long diaryId, final String title, final String content) {

        final DiaryEntity findDiaryEntity = diaryRepository.findById(diaryId).orElseThrow(
                () -> new EntityNotFoundException("해당 일기를 찾을 수 없습니다.")
        );

        findDiaryEntity.updateDiary(title, content);
    }

    @Transactional
    public void deleteDiary(final long diaryId) {

        diaryRepository.deleteById(diaryId);
    }
}
