package codesquad;

import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import support.test.AcceptanceTest;

import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class WebMvcConfigTest extends AcceptanceTest {

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = getLogger(WebMvcConfigTest.class);

    @Test
    public void test() {
        logger.debug("Message : {}", messageSource.getMessage("error.not.supported", null, Locale.getDefault()));
    }
}
