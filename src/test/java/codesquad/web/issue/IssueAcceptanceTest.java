package codesquad.web.issue;

import codesquad.domain.issue.Issue;
import codesquad.domain.issue.IssueRepository;
import codesquad.domain.milestone.Milestone;
import org.junit.Before;
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
import static codesquad.domain.UserTest.JUNGHYUN;
import static codesquad.domain.milestone.MilestoneTest.MILESTONES;
import static org.slf4j.LoggerFactory.getLogger;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = getLogger(IssueAcceptanceTest.class);

    HttpEntity<MultiValueMap<String, Object>> updateRequest;
    HttpEntity<MultiValueMap<String, Object>> deleteRequest;

    @Autowired
    private IssueRepository issueRepository;

    @Before
    public void setUp() throws Exception {
        updateRequest = HtmlFormDataBuilder.urlEncodedForm().put()
                .addParameter("subject", UPDATE_ISSUE_BODY.getSubject())
                .addParameter("comment", UPDATE_ISSUE_BODY.getComment())
                .build();

        deleteRequest = HtmlFormDataBuilder.urlEncodedForm().delete().build();
    }

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
        ResponseEntity<String> response = template.getForEntity(ISSUE.generateUrl(), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().contains(ISSUE.getWriter().getName())).isTrue();
        for (Milestone milestone : MILESTONES) {
            softly.assertThat(response.getBody().contains(milestone.getMilestoneBody().getSubject())).isTrue();
        }
        log.debug("reponse : {}", response.getBody());
    }

    @Test
    public void show_없는이슈찾을때() {
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d", WRONG_ISSUE_ID), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void update() {
        String location = createResource("/api/issues", BRAD, ISSUE_BODY);
        Issue createdIssue = getResource(location, Issue.class, BRAD);
        ResponseEntity<String> response = basicAuthTemplate().postForEntity(createdIssue.generateUrl(), updateRequest, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().contains(UPDATE_ISSUE_BODY.getSubject())).isEqualTo(true);
        softly.assertThat(response.getBody().contains(UPDATE_ISSUE_BODY.getComment())).isEqualTo(true);
        log.debug("reponse : {}", response.getBody());
    }

    @Test
    public void update_로그인안한유저() {
        ResponseEntity<String> responseEntity = template().postForEntity(ISSUE.generateUrl(), updateRequest, String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void update_다른유저() {
        ResponseEntity<String> responseEntity = basicAuthTemplate(JUNGHYUN).postForEntity(ISSUE.generateUrl(), updateRequest, String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void updateForm() {
        ResponseEntity<String> responseEntity = basicAuthTemplate().getForEntity(String.format("/issues/%d/form", ISSUE.getId()), String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(responseEntity.getBody().contains(ISSUE_BODY.getSubject())).isTrue();
        softly.assertThat(responseEntity.getBody().contains(ISSUE_BODY.getComment())).isTrue();
        log.debug("response : {}", responseEntity.getBody());
    }

    @Test
    public void updateForm_다른유저() {
        ResponseEntity<String> responseEntity = basicAuthTemplate(JUNGHYUN).getForEntity(String.format("/issues/%d/form", ISSUE.getId()), String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void updateForm_로그인안한유저() {
        ResponseEntity<String> responseEntity = template().getForEntity(String.format("/issues/%d/form", ISSUE.getId()), String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void delete() {
        String location = createResource("/api/issues", BRAD, ISSUE_BODY);
        Issue createdIssue = getResource(location, Issue.class, BRAD);

        ResponseEntity<String> response = basicAuthTemplate().postForEntity(createdIssue.generateUrl(), deleteRequest, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void delete_로그인안한유저() {
        String location = createResource("/api/issues", BRAD, ISSUE_BODY);
        Issue createdIssue = getResource(location, Issue.class, BRAD);

        ResponseEntity<String> response = template().postForEntity(createdIssue.generateUrl(), deleteRequest, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void delete_다른유저() {
        String location = createResource("/api/issues", BRAD, ISSUE_BODY);
        Issue createdIssue = getResource(location, Issue.class, BRAD);

        ResponseEntity<String> response = template().postForEntity(createdIssue.generateUrl(), deleteRequest, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }
}