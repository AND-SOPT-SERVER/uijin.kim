package org.sopt.week1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final Map<Long, String> trash = new HashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    void save(final Diary diary) {
        // 채번 과정
        final long id = numbering.addAndGet(1);
        // 저장 과정
        storage.put(id, diary.getBody());
    }

    List<Diary> findAll() {
        // (1) diary를 담을 자료구조
        final List<Diary> diaryList = new ArrayList<>();
        // (2) 저장한 값을 불러오는 반복 구조
        for (long index = 1; index <= numbering.intValue(); index++) {
            final String body = storage.get(index);
            // (2-1) 저장한 값을 불러오는 반복 구조
            if (body != null) {
                diaryList.add(new Diary(index, body));
            }
        }
        // (3) 불러온 자료구조를 응답
        return diaryList;
    }

    void deleteById(final Long id) {
        isValidateId(id);
        trash.put(id, storage.get(id));
        storage.remove(id);
    }

    void updateById(final Long id, final String body) {
        isValidateId(id);
        storage.replace(id, body);
    }

    void restoreAll() {
        for (Long id : trash.keySet()) {
            storage.put(id, trash.get(id));
        }
        trash.clear();
    }

    void isValidateId(final Long id) {
        if (storage.get(id) == null) {
            throw new IllegalArgumentException("존재하지 않는 일기 id입니다.");
        }
    }
}
