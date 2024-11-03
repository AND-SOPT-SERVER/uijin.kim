package org.sopt.week3.service.diary;

import org.sopt.week3.dto.response.DiariesResponse;
import org.sopt.week3.dto.response.DiaryDetailResponse;
import org.sopt.week3.dto.response.DiaryResponse;
import org.sopt.week3.enums.entity.Category;
import org.sopt.week3.enums.entity.Criteria;
import org.sopt.week3.enums.response.ErrorMessage;
import org.sopt.week3.exception.BadRequestException;
import org.sopt.week3.exception.NotFoundException;
import org.sopt.week3.repository.diary.DiaryEntity;
import org.sopt.week3.repository.diary.DiaryRepository;
import org.sopt.week3.repository.user.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final UserRepository userRepository;

    public DiaryService(final UserRepository userRepository, final DiaryRepository diaryRepository) {
        this.userRepository = userRepository;
        this.diaryRepository = diaryRepository;
    }

    @Transactional
    public void createDiary(final long userId, final String title, final String content, final Category category, final boolean isVisible) {
        final DiaryDomain findDiaryDomain = convertToDomain(diaryRepository.findFirstByOrderByDateDesc());

        if (checkLastCreateDiaryTime(findDiaryDomain)) {
            diaryRepository.save(
                    DiaryEntity.builder()
                            .userEntity(userRepository.findById(userId))
                            .title(title)
                            .content(content)
                            .category(category)
                            .isVisible(isVisible)
                            .build()
            );
        } else {
            throw new BadRequestException(ErrorMessage.INPUT_IN_LIMIT_TIME);
        }
    }

    @Transactional(readOnly = true)
    public DiariesResponse getDiaries(final long userId, final Category category, final Criteria criteria, final int page, final int size) {

        final Pageable pageable = PageRequest.of(page, size);

        final List<DiaryDomain> diaryDomains = fetchDiaries(category, criteria, pageable).stream()
                .map(this::convertToDomain)
                .toList();

        return DiariesResponse.of(diaryDomains.stream()
                .map(diaryDomain -> DiaryResponse.of(diaryDomain.id(), diaryDomain.title(), diaryDomain.userEntity().getNickname(), diaryDomain.date()))
                .toList());
    }

    @Transactional(readOnly = true)
    public DiaryDetailResponse getDiary(final long diaryId) {
        final DiaryDomain diaryDomain = convertToDomain(diaryRepository.findById(diaryId)
                .orElseThrow(
                        () -> new NotFoundException(ErrorMessage.NOT_FOUND_DIARY)
                ));
        return DiaryDetailResponse.of(diaryDomain.id(), diaryDomain.title(), diaryDomain.content(), diaryDomain.category(), diaryDomain.date());
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

    private List<DiaryEntity> fetchDiaries(final Category category, final Criteria criteria, final Pageable pageable) {
        if (category.equals(Category.ALL)) {
            return diaryRepository.findAll(pageable, criteria.getCriteria());
        } else {
            return diaryRepository.findAllByCategory(category, pageable, criteria.getCriteria());
        }
    }

    private DiaryDomain convertToDomain(final DiaryEntity diaryEntity) {
        return DiaryDomain.of(diaryEntity.getId(), diaryEntity.getUserEntity(), diaryEntity.getTitle(), diaryEntity.getContent(), diaryEntity.getCategory(), diaryEntity.isVisible(), diaryEntity.getDate());
    }

    private boolean checkLastCreateDiaryTime(final DiaryDomain diaryDomain) {
        // 매직넘버 처리 어떻게 하지...
        // 현재 생성된 일기가 없거나, 생성한 지 5분이내이면 일기 생성 가능
        return diaryDomain == null || ChronoUnit.MINUTES.between(diaryDomain.date(), LocalDateTime.now()) <= 5;
    }
}
