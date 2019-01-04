package support.converter;

import org.junit.Test;

import java.time.LocalDateTime;

public class LocalDateTimeConverterTest  {

    @Test
    public void 날짜시간_컨버터() {
        String date = "2019-01-26T13:01";
        String date2 = "2020-01-26T13:01";
        if(LocalDateTime.parse(date).isAfter(LocalDateTime.parse(date2))) {
            System.out.println("X");
        } else {
            System.out.println("O");
        }
    }
}
