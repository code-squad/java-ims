package codesquad.web;

import codesquad.domain.milestone.Milestone;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static codesquad.domain.milestone.MilestoneTest.MILESTONE;
import static codesquad.domain.milestone.MilestoneTest.MILESTONE_BODY;
import static org.slf4j.LoggerFactory.getLogger;

public class ApiMilestoneControllerTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiMilestoneControllerTest.class);

    @Test
    public void create() {
        String location = createResource("/api/milestones", MILESTONE_BODY);
        ResponseEntity<Milestone> response = template.getForEntity(location, Milestone.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().hasSameBody(MILESTONE_BODY)).isTrue();
    }
}