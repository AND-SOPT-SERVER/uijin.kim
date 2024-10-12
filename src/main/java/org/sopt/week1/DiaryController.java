package org.sopt.week1;

import java.util.List;

public class DiaryController {
    private Status status = Status.READY;
    private final DiaryService diaryService = new DiaryService();

    Status getStatus() {
        return status;
    }

    void boot() {
        this.status = Status.RUNNING;
    }

    void finish() {
        this.status = Status.FINISHED;
    }

    // APIS
    final List<Diary> getList() {
        return diaryService.getDiaryList();
    }

    final void post(final String body) {
        if (TextUtils.getLengthOfBody(body) > 30) {
            throw new IllegalArgumentException("30자 이하로 적어주세요");

        }
        diaryService.writeDiary(body);
    }

    final void delete(final String id) {
        diaryService.deleteDiary(Long.parseLong(id));
    }

    final void patch(final String id, final String body) {
        diaryService.updateDiary(Long.parseLong(id), body);
    }

    final void restore() {
        diaryService.restoreDiaryList();
    }


    enum Status {
        READY,
        RUNNING,
        FINISHED,
        ERROR,
    }
}
