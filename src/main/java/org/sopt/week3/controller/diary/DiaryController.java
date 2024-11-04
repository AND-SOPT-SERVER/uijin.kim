package org.sopt.week3.controller.diary;

import jakarta.validation.Valid;
import org.sopt.week3.dto.request.DiaryInformationRequest;
import org.sopt.week3.dto.response.DiariesResponse;
import org.sopt.week3.dto.response.DiaryDetailResponse;
import org.sopt.week3.enums.entity.Category;
import org.sopt.week3.enums.entity.Criteria;
import org.sopt.week3.service.diary.DiaryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping
    public ResponseEntity<Void> createDiary(
            @RequestHeader(name = "userId") final long userId,
            @RequestBody @Valid final DiaryInformationRequest diaryInformationRequest
    ) {
        diaryService.createDiary(userId, diaryInformationRequest.title(), diaryInformationRequest.content(), diaryInformationRequest.category(), diaryInformationRequest.isVisible());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping
    public ResponseEntity<DiariesResponse> getDiaries(
            @RequestParam(name = "category") final Category category,
            @RequestParam(name = "criteria") final Criteria criteria,
            @RequestParam(name = "page") final int page,
            @RequestParam(name = "size") final int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(diaryService.getDiaries(category, criteria, page, size));
    }

    @GetMapping("/me")
    public ResponseEntity<DiariesResponse> getMyDiaries(
            @RequestHeader(name = "userId") final long userId,
            @RequestParam(name = "category") final Category category,
            @RequestParam(name = "criteria") final Criteria criteria,
            @RequestParam(name = "page") final int page,
            @RequestParam(name = "size") final int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(diaryService.getMyDiaries(userId, category, criteria, page, size));
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryDetailResponse> getDiary(
            @RequestHeader(name = "userId") final long userId,
            @PathVariable(value = "diaryId") final long diaryId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(diaryService.getDiary(diaryId));
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<Void> updateDiary(
            @RequestHeader(name = "userId") final long userId,
            @PathVariable(value = "diaryId") final long diaryId,
            @RequestBody final DiaryInformationRequest diaryInformationRequest
    ) {
        diaryService.updateDiary(diaryId, diaryInformationRequest.title(), diaryInformationRequest.content());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @RequestHeader(name = "userId") final long userId,
            @PathVariable(value = "diaryId") final long diaryId
    ) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
