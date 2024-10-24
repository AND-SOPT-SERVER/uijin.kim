package org.sopt.week2.controller;

import org.sopt.week2.dto.request.DiaryInformationRequest;
import org.sopt.week2.dto.response.DiariesResponse;
import org.sopt.week2.dto.response.DiaryDetailResponse;
import org.sopt.week2.enums.entity.DiaryCategory;
import org.sopt.week2.service.DiaryService;
import org.sopt.week2.util.TextUtils;
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
            @RequestBody final DiaryInformationRequest diaryInformationRequest
    ) {
        TextUtils.validateDiaryContent(diaryInformationRequest.content());
        diaryService.createDiary(diaryInformationRequest.title(), diaryInformationRequest.content(), diaryInformationRequest.diaryCategory());
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping
    public ResponseEntity<DiariesResponse> getDiaries(
            @RequestParam(value = "diaryCategory") final DiaryCategory diaryCategory
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(DiariesResponse.of(diaryService.getDiaries(diaryCategory)));
    }

    @GetMapping("/{diaryId}")
    public ResponseEntity<DiaryDetailResponse> getDiary(
            @PathVariable(value = "diaryId") final long diaryId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(diaryService.getDiary(diaryId));
    }

    @PatchMapping("/{diaryId}")
    public ResponseEntity<Void> updateDiary(
            @PathVariable(value = "diaryId") final long diaryId,
            @RequestBody final DiaryInformationRequest diaryInformationRequest
    ) {
        TextUtils.validateDiaryContent(diaryInformationRequest.content());
        diaryService.updateDiary(diaryId, diaryInformationRequest.title(), diaryInformationRequest.content());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

    @DeleteMapping("/{diaryId}")
    public ResponseEntity<Void> deleteDiary(
            @PathVariable(value = "diaryId") final long diaryId
    ) {
        diaryService.deleteDiary(diaryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}
