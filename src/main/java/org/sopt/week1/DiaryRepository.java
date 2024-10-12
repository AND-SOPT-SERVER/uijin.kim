package org.sopt.week1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DiaryRepository {
    File diaryFile = new File("diary.txt");
    File idFile = new File("id.txt");
    File trashFile = new File("trash.txt");
    File tempFile = new File("tempDiary.txt");

    void save(final Diary diary) {

        long lastId = loadLastId();
        final long id = lastId + 1;

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(diaryFile, true));

            if (diaryFile.isFile() && diaryFile.canWrite()) {
                bufferedWriter.write(id + " " + diary.getBody());
                bufferedWriter.newLine();
                bufferedWriter.close();
            }

            saveLastId(id);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    List<Diary> findAll() {
        final List<Diary> diaryList = new ArrayList<>();

        try {
            FileReader fileReader = new FileReader(diaryFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String diaryFileInfo = "";

            while ((diaryFileInfo = bufferedReader.readLine()) != null) {
                List<String> diaryInfo = TextUtils.splitDiaryInfo(diaryFileInfo);
                diaryList.add(new Diary(Long.parseLong(diaryInfo.get(0)), diaryInfo.get(1)));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return diaryList;
    }

    void deleteById(final Long id) {
        isValidateId(id);
        try {
            // diaryFile에서 삭제할 ID를 제외하고 임시 파일에 저장
            BufferedWriter tempWriter = new BufferedWriter(new FileWriter(tempFile));
            BufferedReader diaryReader = new BufferedReader(new FileReader(diaryFile));
            BufferedWriter trashWriter = new BufferedWriter(new FileWriter(trashFile, true));

            String currentLine;
            boolean isFound = false;

            while ((currentLine = diaryReader.readLine()) != null) {
                List<String> diaryInfo = TextUtils.splitDiaryInfo(currentLine);
                if (diaryInfo.get(0).equals(id.toString())) {
                    // 삭제할 Diary를 trash.txt에 저장
                    trashWriter.write(currentLine);
                    trashWriter.newLine();
                    isFound = true;  // ID를 찾았음을 표시
                } else {
                    tempWriter.write(currentLine);
                    tempWriter.newLine();
                }
            }

            tempWriter.close();
            diaryReader.close();
            trashWriter.close();

            // ID가 존재했으면 임시 파일을 원본 파일로 대체
            if (isFound) {
                diaryFile.delete();
                tempFile.renameTo(diaryFile);
            } else {
                // ID가 존재하지 않을 경우 임시 파일을 삭제
                tempFile.delete();
                throw new IllegalArgumentException("해당 ID의 Diary가 존재하지 않습니다.");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void updateById(final Long id, final String body) {
        isValidateId(id);
        try {
            BufferedWriter tempWriter = new BufferedWriter(new FileWriter(tempFile));
            BufferedReader diaryReader = new BufferedReader(new FileReader(diaryFile));

            String currentLine;
            boolean isFound = false;

            while ((currentLine = diaryReader.readLine()) != null) {
                List<String> diaryInfo = TextUtils.splitDiaryInfo(currentLine);
                if (diaryInfo.get(0).equals(id.toString())) {
                    // 수정된 내용으로 기록
                    tempWriter.write(id + " " + body);
                    tempWriter.newLine();
                    isFound = true;  // ID를 찾았음을 표시
                } else {
                    tempWriter.write(currentLine);
                    tempWriter.newLine();
                }
            }

            tempWriter.close();
            diaryReader.close();

            // ID가 존재했으면 임시 파일을 원본 파일로 대체
            if (isFound) {
                diaryFile.delete();
                tempFile.renameTo(diaryFile);
            } else {
                // ID가 존재하지 않을 경우 임시 파일을 삭제
                tempFile.delete();
                throw new IllegalArgumentException("해당 ID의 Diary가 존재하지 않습니다.");
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    void restoreAll() {
        try {
            BufferedReader trashReader = new BufferedReader(new FileReader(trashFile));
            BufferedWriter diaryWriter = new BufferedWriter(new FileWriter(diaryFile, true)); // append mode로 diary.txt에 추가

            String currentLine;
            boolean isRestored = false;

            while ((currentLine = trashReader.readLine()) != null) {
                diaryWriter.write(currentLine);
                diaryWriter.newLine();
                isRestored = true; // 복원 작업이 있음을 표시
            }

            trashReader.close();
            diaryWriter.close();

            // 복원이 완료되었으면 trash.txt 파일 비우기
            if (isRestored) {
                BufferedWriter trashCleaner = new BufferedWriter(new FileWriter(trashFile)); // trash.txt 초기화
                trashCleaner.write("");
                trashCleaner.close();
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }


    void isValidateId(final Long id) {
        List<Long> diaryIds = new ArrayList<Long>();

        try {
            FileReader fileReader = new FileReader(diaryFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String diaryFileInfo = "";


            while ((diaryFileInfo = bufferedReader.readLine()) != null) {
                List<String> diaryInfo = TextUtils.splitDiaryInfo(diaryFileInfo);
                diaryIds.add(Long.parseLong(diaryInfo.get(0)));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (!diaryIds.contains(id)) {
            throw new IllegalArgumentException("존재하지 않는 일기 id입니다.");
        }
    }

    private long loadLastId() {
        long lastId = 0;
        if (idFile.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(idFile));
                String idStr = bufferedReader.readLine();  // ID 값을 한 줄로 저장
                if (idStr != null) {
                    lastId = Long.parseLong(idStr);
                }
                bufferedReader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        return lastId;
    }

    private void saveLastId(long id) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(idFile));
            bufferedWriter.write(Long.toString(id));
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }

}
