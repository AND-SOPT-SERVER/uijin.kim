package org.sopt.week2.service;

import jakarta.persistence.EntityNotFoundException;
import org.sopt.week2.dto.response.DiaryDetailResponse;
import org.sopt.week2.dto.response.DiaryResponse;
import org.sopt.week2.enums.response.ErrorMessage;
import org.sopt.week2.exception.BadRequestException;
import org.sopt.week2.repository.DiaryEntity;
import org.sopt.week2.repository.DiaryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(final DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public void createDiary(final String title, final String content) {

        final DiaryEntity findDiaryEntity = diaryRepository.findFirstByOrderByCreateAtDesc();

        if (checkLastCreateDiaryTime(findDiaryEntity)) {
            diaryRepository.save(new DiaryEntity(title, content));
        } else {
            throw new BadRequestException(ErrorMessage.INPUT_IN_LIMIT_TIME);
        }
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

        final DiaryDomain diaryDomain = new DiaryDomain(findDiaryEntity.getId(), findDiaryEntity.getTitle(), findDiaryEntity.getContent(), findDiaryEntity.getCreateAt(), findDiaryEntity.getUpdateAt());

        findDiaryEntity.updateDiary(title, content);
    }

    @Transactional
    public void deleteDiary(final long diaryId) {

        diaryRepository.deleteById(diaryId);
    }

    private boolean checkLastCreateDiaryTime(final DiaryEntity diaryEntity) {
        // 현재 생성된 일기가 없거나, 생성한 지 5분이내이면 일기 생성 가능
        return diaryEntity == null || ChronoUnit.MINUTES.between(diaryEntity.getCreateAt(), LocalDateTime.now()) <= 5;
    }
}
