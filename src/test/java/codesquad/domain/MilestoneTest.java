package codesquad.domain;

import codesquad.dto.MilestoneDto;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MilestoneTest {
    @Test
    public void convertDate() {
        String subject = "test subject";
        String startDate = "2018-07-21T01:59";
        String endDate = "2018-10-24T11:58";

        MilestoneDto milestoneDto = new MilestoneDto(subject, startDate, endDate);
        Milestone milestone = new Milestone(milestoneDto);

        LocalDateTime startLocalDate = LocalDateTime.of(2018, 7, 21, 1, 59);
        LocalDateTime endLocalDate = LocalDateTime.of(2018, 10, 24, 11, 58);

        assertThat(startLocalDate, is(milestone.getStartDate()));
        assertThat(endLocalDate, is(milestone.getEndDate()));
    }
}
