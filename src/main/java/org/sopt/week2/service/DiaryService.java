package org.sopt.week2.service;

import org.sopt.week2.dto.response.DiariesResponse;
import org.sopt.week2.dto.response.DiaryDetailResponse;
import org.sopt.week2.dto.response.DiaryResponse;
import org.sopt.week2.enums.entity.DiaryCategory;
import org.sopt.week2.enums.response.ErrorMessage;
import org.sopt.week2.exception.BadRequestException;
import org.sopt.week2.exception.NotFoundException;
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
    public void createDiary(final String title, final String content, final DiaryCategory diaryCategory) {

        final List<DiaryEntity> diaryEntities = diaryRepository.findAll();
        final List<DiaryDomain> diaryDomains = convertDiaryEntitiesToDiaryDomains(diaryEntities);

        checkDuplicatedTitle(diaryDomains, title);

        final DiaryEntity findDiaryEntity = diaryRepository.findFirstByOrderByCreateAtDesc();
        if (checkLastCreateDiaryTime(findDiaryEntity)) {
            diaryRepository.save(new DiaryEntity(title, content, diaryCategory));
        } else {
            throw new BadRequestException(ErrorMessage.INPUT_IN_LIMIT_TIME);
        }
    }

    @Transactional(readOnly = true)
    public DiariesResponse getDiaries(final DiaryCategory diaryCategory) {

        final List<DiaryEntity> diaryEntities = fetchDiariesByCategory(diaryCategory);
        final List<DiaryDomain> diaryDomains = convertDiaryEntitiesToDiaryDomains(diaryEntities);

        return DiariesResponse.of(diaryDomains.stream()
                .map(diaryDomain -> DiaryResponse.of(diaryDomain.id(), diaryDomain.title()))
                .toList());
    }

    @Transactional(readOnly = true)
    public DiaryDetailResponse getDiary(final long diaryId) {

        final DiaryEntity findDiaryEntity = diaryRepository.findById(diaryId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.NOT_FOUND_DIARY)
        );

        final DiaryDomain diaryDomain = DiaryDomain.of(findDiaryEntity.getId(), findDiaryEntity.getTitle(), findDiaryEntity.getContent(), findDiaryEntity.getCreateAt(), findDiaryEntity.getUpdateAt());
        return DiaryDetailResponse.of(diaryDomain.id(), diaryDomain.title(), diaryDomain.content(), diaryDomain.createAt());
    }

    @Transactional
    public void updateDiary(final long diaryId, final String title, final String content) {

        final DiaryEntity findDiaryEntity = diaryRepository.findById(diaryId).orElseThrow(
                () -> new NotFoundException(ErrorMessage.NOT_FOUND_DIARY)
        );

        findDiaryEntity.updateDiary(title, content);
    }

    @Transactional
    public void deleteDiary(final long diaryId) {

        diaryRepository.deleteById(diaryId);
    }

    private List<DiaryEntity> fetchDiariesByCategory(final DiaryCategory diaryCategory) {
        if (diaryCategory.equals(DiaryCategory.ALL)) {
            return diaryRepository.findTop10ByOrderByContentLengthAndUpdateAtDesc();
        } else {
            return diaryRepository.findTop10ByDiaryCategoryOrderByContentLengthAndUpdateAtDesc(diaryCategory);
        }
    }

    private List<DiaryDomain> convertDiaryEntitiesToDiaryDomains(final List<DiaryEntity> diaryEntities) {
        return diaryEntities.stream()
                .map(diaryEntity -> new DiaryDomain(diaryEntity.getId(), diaryEntity.getTitle(), diaryEntity.getContent(), diaryEntity.getCreateAt(), diaryEntity.getUpdateAt()))
                .toList();
    }

    private void checkDuplicatedTitle(final List<DiaryDomain> diaryDomains, final String title) {
        for (DiaryDomain diaryDomain : diaryDomains) {
            diaryDomain.checkDuplicatedTitle(title);
        }
    }

    private boolean checkLastCreateDiaryTime(final DiaryEntity diaryEntity) {
        // 매직넘버 처리 어떻게 하지...
        // 현재 생성된 일기가 없거나, 생성한 지 5분이내이면 일기 생성 가능
        return diaryEntity == null || ChronoUnit.MINUTES.between(diaryEntity.getCreateAt(), LocalDateTime.now()) <= 5;
    }
}
