package codesquad.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MilestoneTest {
    private static final Logger log = LoggerFactory.getLogger(MilestoneTest.class);

    @Test
    public void create() {
        Milestone milestone = new Milestone();
    }

    // datetime-local은 html5 date picker의 이름
    @Test
    public void convert_datetime_local_to_localdatetime() {
        String unconvertedTime = "2017-06-01T08:30";
        LocalDateTime convertedTime = LocalDateTime.parse(unconvertedTime, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        log.debug("convert time : {}", convertedTime);
    }
}
