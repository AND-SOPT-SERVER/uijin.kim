import org.junit.jupiter.api.*;
import org.sopt.week1.TextUtils;

@DisplayName("일기 글자 수 세기 테스트")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TextUtilsTest {

    @Test
    void 이모지는_한_개의_문자로_인식한다() {
        //given
        final String test1 = "😃행복한 하루였어요👍";
        final String test2 = "🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻🤞🏻";
        final int expected1 = 11;
        final int expected2 = 31;

        //when
        final int actual1 = TextUtils.getLengthOfBody(test1);
        final int actual2 = TextUtils.getLengthOfBody(test2);

        //then
        Assertions.assertEquals(actual1, expected1);
        Assertions.assertEquals(actual2, expected2);

    }
}
