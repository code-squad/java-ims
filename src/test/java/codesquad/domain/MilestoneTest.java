package codesquad.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MilestoneTest {
    private static final Logger log = LoggerFactory.getLogger(MilestoneTest.class);

    @Test
    public void create() {
        Milestone milestone = new Milestone();
    }

    // datetime-local은 html5 date picker의 이름
    @Test
    public void convert_datetime_local_to_localdatetime() {
        String endDate = "2017-06-01T08:30";
        Milestone milestone = new Milestone();
        milestone.setHtml_enddate(endDate);

        LocalDateTime convertedDate = LocalDateTime.parse(endDate, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

        assertThat(milestone.getEndDate(),is(convertedDate));
    }

    @Test
    public void getDueDate() {
        String date = "2017-06-01T08:30";
        LocalDateTime endDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        String result = endDate.format(DateTimeFormatter.ofPattern("'Due by' MMM dd, yyyy"));

        System.out.println(result);
    }
}
