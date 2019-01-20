package codesquad.web;

import codesquad.service.MilestoneService;
import codesquad.web.api.ApiCommentAcceptanceTest;
import org.junit.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.time.LocalDateTime;
import java.util.logging.Logger;

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);


    @Autowired
    private MilestoneService milestoneService;

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response =
                template.getForEntity("/milestones/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void createForm_login() {
        ResponseEntity<String> response =
                basicAuthTemplate().getForEntity("/milestones/form",String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "MILESTONE_1")
//                .addParameter("startDate", LocalDateTime.now().toString())
//                .addParameter("endDate", LocalDateTime.now().toString())
                .build();

        ResponseEntity<String> response =
                basicAuthTemplate().postForEntity("/milestones", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/milestones/list");
    }
}
