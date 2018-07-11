package codesquad.web;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IssueAcceptanceTest extends AcceptanceTest {

    @Test
    public void issueCreateForm_no_login() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);

        assertThat(response.getStatusCode(),is(HttpStatus.OK));
    }
}
