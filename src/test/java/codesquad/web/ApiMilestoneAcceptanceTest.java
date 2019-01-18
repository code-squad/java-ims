package codesquad.web;

import codesquad.domain.*;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiMilestoneAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiMilestoneAcceptanceTest.class);

    private Milestone milestone;
    private Issue issue;

    @Before
    public void setUp() throws Exception {
        this.milestone = new Milestone("힘을내요 슈퍼파워");
        this.issue = IssueTest.FIRST_ISSUE;
    }

    @Test
    public void create() {
        ResponseEntity<Milestone> response = basicAuthTemplate().postForEntity("/api/milestones", milestone, Milestone.class);


        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        softly.assertThat(response.getBody().getSubject()).isEqualTo("힘을내요 슈퍼파워");

        String location = response.getHeaders().getLocation().getPath();
        log.debug("location : {} ~~~" , location);
        ResponseEntity<String> response2 = template.getForEntity(location, String.class);
        softly.assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response2.getBody()).isNotNull();
    }

    @Test
    public void show_list() {
        String location = createResource("/api/milestones", milestone);

        ResponseEntity<List> response = template()
                .getForEntity("/api/issues/" + issue.getId() + "/milestones", List.class);
        Map data = (Map) response.getBody().get(response.getBody().size() - 1);
        System.out.println("### : " + data);

        softly.assertThat(data.get("subject")).isEqualTo(milestone.getSubject());
    }

    @Test
    public void designateMilestone() {
        String location = createResource("/api/milestones", milestone);
        log.debug("#####~~~~ : {}" ,milestone.getId());
        ResponseEntity<Issue> response = basicAuthTemplate()
                .postForEntity("/api/issues/" + issue.getId() + "/milestones/" + location, milestone, Issue.class);
        log.debug("##### : {}", response);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().getMilestone().getSubject()).isEqualTo(milestone.getSubject());
    }

    @Test
    public void designateMilestoneNoLogin() {
        String location = createResource("/api/milestones", milestone);
        ResponseEntity<Issue> response = template()
                .postForEntity("/api/issues/" + issue.getId() + "/milestones/" + location, milestone, Issue.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
