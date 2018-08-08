package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.util.HashMap;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MilestoneAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(MilestoneAcceptanceTest.class);

    @Test
    public void list() {
        ResponseEntity<String> response = template.getForEntity("/milestones", String.class);
        assertThat(response.getStatusCode(),is(HttpStatus.OK));
    }

    @Test
    public void createForm() {
        TestRestTemplate template = basicAuthTemplate(findDefaultUser());
        ResponseEntity<String> response = template.getForEntity("/milestones/form", String.class);
        assertThat(response.getStatusCode(),is(HttpStatus.OK));
    }

    public void create() {
        TestRestTemplate template = basicAuthTemplate(findDefaultUser());
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "생성된 첫 번째 이슈 제목")
                .addParameter("startDate", "생성된 첫 번째 이슈 내용")
                .addParameter("endDate", "생성된 첫 번째 이슈 내용").build();
        ResponseEntity<String> response = template.postForEntity("/milestones", request, String.class);
        assertThat(response.getStatusCode(),is(HttpStatus.OK));
    }

    @Test
    public void show() {
        Long id = 1L;
        ResponseEntity<String> response = template.getForEntity("/milestones/" + id, String.class);
        assertThat(response.getStatusCode() ,is(HttpStatus.OK));
    }
}
