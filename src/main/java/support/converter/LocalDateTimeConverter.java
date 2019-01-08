package support.converter;

import org.slf4j.Logger;
import org.springframework.core.convert.converter.Converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public final class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
private static final Logger logger = getLogger(LocalDateTimeConverter.class);

    @Override
    public LocalDateTime convert(String source) {
        if (source == null || source.isEmpty()) {
            return null;
        }
        logger.info("formatter : " + source);
        return LocalDateTime.parse(source);
    }
}
