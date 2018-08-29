package codesquad.web;

import codesquad.domain.Comment;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(ApiIssueAcceptanceTest.class);

    @Test
    public void setMilestone() {
        Long issueId = 1L;
        Long milestoneId = 1L;
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/api/issues/" + issueId + "/milestones/" + milestoneId, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setLabel() {
        Long issueId = 1L;
        Long labelId = 1L;
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/api/issues/" + issueId + "/labels/" + labelId, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void setAsignee() {
        Long issueId = 1L;
        Long assigneeId = 1L; // equal to userId
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/api/issues/" + issueId + "/users/" + assigneeId, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateComment() {
        Long issueId = 1L;
        Long commentId = 1L;
        String createUrl = "/api/issues/"+issueId+"/comments/";
        String updateUrl = "/api/issues/"+issueId+"/comments/"+commentId;

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("contents", "댓글 문제가 아닌데요?")
                .build();

//        ResponseEntity<String> response = basicAuthTemplate().postForObject(createUrl, request, Comment.class);
//        assertTrue(createdComment.toString().contains("댓글 문제가 아닌데요?"));
//        log.debug("created Comment : {}", createdComment.toString());
//        assertThat(createdComment.toString().contains("댓글 문제가 아닌데요?"), is(true));
    }
}
