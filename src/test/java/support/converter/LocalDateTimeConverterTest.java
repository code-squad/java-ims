package support.converter;

import org.junit.Test;
import org.slf4j.Logger;

import java.time.LocalDateTime;

import static org.slf4j.LoggerFactory.getLogger;

public class LocalDateTimeConverterTest {
    private static final Logger log = getLogger(LocalDateTimeConverterTest.class);

    @Test
    public void localDateTime일때() {
        String localDateTiemSample = "2222-02-22T14:02";
        log.info(LocalDateTime.parse(localDateTiemSample).toString());
    }
}