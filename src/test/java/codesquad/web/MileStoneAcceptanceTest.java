package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MileStoneAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(MileStoneAcceptanceTest.class);

    @Test
    public void list() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/milestone/list", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
