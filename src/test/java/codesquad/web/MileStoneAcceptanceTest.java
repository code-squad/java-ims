package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MileStoneAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(MileStoneAcceptanceTest.class);

    @Test
    public void list() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/milestones", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "mileStone test")
                .addParameter("startDate", "2018-06-01T08:30")
                .addParameter("endDate", "2018-07-01T08:30").build();
        log.info("request body : {}", request.getBody());
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/milestones", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
    }
}
