package codesquad.web;

import codesquad.domain.Milestone;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiMilestoneAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiMilestoneAcceptanceTest.class);

    @Test
    public void create() {
        Milestone milestone = new Milestone("힘을내요 슈퍼파워");
       /* ResponseEntity<String> responseEntity = basicAuthTemplate()
                .postForEntity("/api/milestones", milestone, String.class);*/
        ResponseEntity<Milestone> response = basicAuthTemplate().postForEntity("/api/milestones", milestone, Milestone.class);


        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        softly.assertThat(response.getBody().getSubject()).isEqualTo("힘을내요 슈퍼파워");

        String location = response.getHeaders().getLocation().getPath();
        log.debug("location : {} ~~~" , location);
        ResponseEntity<String> response2 = template.getForEntity(location, String.class);
        softly.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response2.getBody()).isNotNull();
    }
}
