package org.sopt.week1;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextUtils {

    private static final Pattern graphemePattern = Pattern.compile("\\X");
    private static final Matcher graphemeMatcher = graphemePattern.matcher("");

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
}
