package org.sopt.week1;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    private static final Pattern graphemePattern = Pattern.compile("\\X");
    private static final Matcher graphemeMatcher = graphemePattern.matcher("");
    private static final String SPLIT_CHARACTERS = " ";

    public static int getLengthOfBody(final String body) {
        if (body == null) {
            return 0;
        }

        graphemeMatcher.reset(body);

        int count = 0;
        while (graphemeMatcher.find()) {
            count++;
        }
        return count;
    }

    public static List<String> splitDiaryInfo(String diaryInfo) {
        return new ArrayList<String>(List.of(diaryInfo.split(SPLIT_CHARACTERS)));
    }
}
