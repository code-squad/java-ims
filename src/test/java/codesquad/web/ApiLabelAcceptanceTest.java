package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueTest;
import codesquad.domain.Label;
import codesquad.domain.UserTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiLabelAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiLabelAcceptanceTest.class);

    private Issue issue;
    private Label label;

    @Before
    public void setUp() throws Exception {
        issue = IssueTest.FIRST_ISSUE;
        Label label = new Label("first label test");
        this.label = label;
    }

    @Test
    public void addLabelToIssue() {
        ResponseEntity<Label> response = basicAuthTemplate().postForEntity("/api/labels", label, Label.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        softly.assertThat(response.getBody().getName()).isEqualTo("first label test");
    }

    @Test
    public void show_list() {
        String location = createResource("/api/labels", label);

        ResponseEntity<List> response = template().getForEntity("/api/issues/"+ issue.getId() + "/labels", List.class);
        Map data = (Map) response.getBody().get(response.getBody().size() - 1);
        log.debug("라벨 어디있는지 테스트 : {}" , response);
        softly.assertThat(data.get("name")).isEqualTo(label.getName());
    }

    @Test
    public void designateLabel() {
        String location = createResource("/api/labels", label);
        log.debug("### {}", location);
        ResponseEntity<Issue> response = basicAuthTemplate()
                .postForEntity("/api/issues/" + issue.getId() + "/labels/" + location, label, Issue.class);
        log.debug("라벨테스트~~~~~~~ : {}", response);

        softly.assertThat(response.getBody().getLabel().getName()).isEqualTo(label.getName());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void designateLabelNoLogin() {
        String location = createResource("/api/labels", label);
        ResponseEntity<Issue> response = template()
                .postForEntity("/api/issues/" + issue.getId() + "/labels" + location, label, Issue.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
