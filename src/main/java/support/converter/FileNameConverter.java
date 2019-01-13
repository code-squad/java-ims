package support.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileNameConverter {

    private static final String SPLIT_STANDARD = ".";

    public static String convert(String fileName) {
        return String.format("$s.%s", String.valueOf((getLocalDateTime() + fileName).hashCode()), obtainSuffix(fileName));
    }

    public static String getLocalDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String obtainSuffix(String fileName) {
        return split(fileName)[split(fileName).length - 1];
    }

    public static String[] split(String fileName) {
        return fileName.split(SPLIT_STANDARD);
    }
}
