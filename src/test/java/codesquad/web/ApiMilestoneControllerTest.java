package codesquad.web;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.milestone.MilestoneBody;
import codesquad.validate.ValidationError;
import codesquad.validate.ValidationErrorsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;
import static support.test.Fixture.MILESTONE_BODY;
import static support.test.ValidationTest.VALIDATOR;

public class ApiMilestoneControllerTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiMilestoneControllerTest.class);

    @Test
    public void create() {
        String location = createResource("/api/milestones", MILESTONE_BODY);
        ResponseEntity<Milestone> response = template.getForEntity(location, Milestone.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().hasSameBody(MILESTONE_BODY)).isTrue();
    }

    @Test
    public void create_invalid() {
        MilestoneBody milestoneBody = new MilestoneBody("a", LocalDateTime.now(), LocalDateTime.now());
        ResponseEntity<ValidationErrorsResponse> response = basicAuthTemplate().postForEntity("/api/milestones", milestoneBody, ValidationErrorsResponse.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        for (ValidationError error : response.getBody().getErrors()) {
            log.debug("error : {}", error);
        }

        Set<ConstraintViolation<MilestoneBody>> violations = VALIDATOR.validate(milestoneBody);
        softly.assertThat(violations.size()).isEqualTo(1);
    }
}