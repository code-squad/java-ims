package codesquad.web;

import codesquad.domain.Label;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiLabelAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiLabelAcceptanceTest.class);

    public void addLabelToIssue() {
        Label label = new Label("first label test");
        ResponseEntity<Label> response = basicAuthTemplate().postForEntity("/api/labels", label, Label.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        softly.assertThat(response.getBody()).isEqualTo("first label test");
    }
}
