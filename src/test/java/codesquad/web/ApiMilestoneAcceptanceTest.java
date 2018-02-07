package codesquad.web;

import codesquad.dto.MilestoneDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.AcceptanceTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiMilestoneAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(ApiMilestoneAcceptanceTest.class);

    @Test
    public void create() throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        MilestoneDto newMileStone = new MilestoneDto()
                .setSubject("test subject")
                .setStartDate(LocalDateTime.parse("2017-04-02 11:11", formatter))
                .setEndDate(LocalDateTime.parse("2017-04-11 11:11", formatter));

        String location = createResource("/api/milestones", newMileStone);

        MilestoneDto dbMilestone = getResource(location, MilestoneDto.class, findDefaultUser());
        assertThat(dbMilestone, is(newMileStone));
    }


}
