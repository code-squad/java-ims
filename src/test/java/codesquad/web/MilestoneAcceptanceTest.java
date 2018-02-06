package codesquad.web;

import codesquad.service.IssueService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MilestoneAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);

    @Test
    public void createForm_login() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/milestones/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        logger.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_no_login() {
        ResponseEntity<String> response = template().getForEntity("/milestones/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void create_login() {
        String subject = "milestone";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("startDate", "2017-12-22")
                .addParameter("endDate", "2018-03-02")
                .build();

        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/milestones", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertTrue(response.getHeaders().getLocation().getPath().startsWith("/milestones/"));
    }

    @Test
    public void create_no_login() {
        String subject = "들어가면 안되";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("startDate", "2017-12-22")
                .addParameter("endDate", "2018-03-02")
                .build();

        ResponseEntity<String> response = template().postForEntity("/milestones", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

//    @Test
//    public void show() {
//        ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);
//
//        logger.debug("body : {}", response.getBody());
//        assertTrue(response.getBody().contains("test issue1"));
//        assertTrue(response.getBody().contains("테스트 1번 이슈입니다."));
//    }
//
//    @Test
//    public void update_login() {
//        String subject = "수정이 되야합니다.";
//        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
//                .put()
//                .addParameter("subject", subject)
//                .addParameter("comment", "코메에에에에에에엔트")
//                .build();
//
//        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/3", request, String.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
//        assertThat(issueService.findById(3L).getSubject(), is(subject));
//        assertThat(response.getHeaders().getLocation().getPath(), is("/issues/3"));
//    }
//
//    @Test
//    public void update_no_login() {
//        String subject = "수정이 되면 안됩니다!!!!!!";
//        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
//                .put()
//                .addParameter("subject", subject)
//                .addParameter("comment", "코메에에에에에에엔트")
//                .build();
//
//        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/2", request, String.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
//        assertNotEquals(issueService.findById(2L).getSubject(), subject);
//    }
//
//    @Test
//    public void delete_login() {
//        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
//                .delete()
//                .build();
//
//        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/5", request, String.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
//        assertNull(issueService.findById(5L));
//        assertThat(response.getHeaders().getLocation().getPath(), is("/"));
//    }
//
//    @Test
//    public void delete_no_login() {
//        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
//                .delete()
//                .build();
//
//        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/4", request, String.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
//        assertNotNull(issueService.findById(4L));
//    }
}