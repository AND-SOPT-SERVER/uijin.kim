package org.sopt.week1;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();

    void writeDiary(final String body) {
        final Diary diary = new Diary(null, body);

        diaryRepository.save(diary);
    }

    List<Diary> getDiaryList() {
        return diaryRepository.findAll();
    }

    void deleteDiary(final Long id) {
        diaryRepository.deleteById(id);
    }

    void updateDiary(final Long id, final String body) {
        diaryRepository.updateById(id, body);
    }

    void restoreDiaryList() {
        diaryRepository.restoreAll();
    }

}
