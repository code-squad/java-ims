package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Test
    public void list() {
        ResponseEntity<String> response = template().getForEntity("/issues", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}