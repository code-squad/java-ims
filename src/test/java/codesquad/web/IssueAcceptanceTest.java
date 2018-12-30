package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static codesquad.domain.IssueTest.*;
import static codesquad.domain.UserTest.BRAD;
import static org.slf4j.LoggerFactory.getLogger;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void createForm_로그인안한유저() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        softly.assertThat(response.getBody().contains("로그인이 필요합니다!"));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create_성공() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "테스트 주제")
                .addParameter("comment", "테스트 내용입니다")
                .build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void create_로그인안한유저() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        softly.assertThat(response.getBody().contains("로그인이 필요합니다!"));
        log.debug("body : {}", response.getBody());
    }


    @Test
    public void create_내용이_너무짧을때() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "테")
                .addParameter("comment", "테")
                .build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void create_내용이_없을때() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "")
                .addParameter("comment", "")
                .build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void show() {
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d", ISSUE.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void show_없는이슈찾을때() {
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d", WRONG_ISSUE_ID), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void update() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().put()
                .addParameter("subject", UPDATE_ISSUE.getSubject())
                .addParameter("comment", UPDATE_ISSUE.getComment())
                .build();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues/" + ISSUE.getId(), request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().contains(UPDATE_ISSUE.getSubject())).isEqualTo(true);
        softly.assertThat(response.getBody().contains(UPDATE_ISSUE.getComment())).isEqualTo(true);
        log.debug("reponse : {}", response.getBody());
    }
}