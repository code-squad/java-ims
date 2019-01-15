package support.converter;

import org.junit.Test;
import support.test.AcceptanceTest;
import support.test.BaseTest;

import java.time.LocalDateTime;

public class LocalDateTimeConverterTest extends BaseTest {

    @Test
    public void 날짜시간_컨버터() {
        String date1 = "2019-01-26T13:01";
        String date2 = "2020-01-26T13:01";

        softly.assertThat(LocalDateTime.parse(date1).isBefore(LocalDateTime.parse(date2))).isTrue();
    }
}
