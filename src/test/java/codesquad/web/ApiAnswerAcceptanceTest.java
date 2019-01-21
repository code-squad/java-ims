package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Issue;
import codesquad.domain.IssueBody;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.*;
import support.test.AcceptanceTest;

import static codesquad.domain.IssueFixture.ISSUE_BODY_JSON_PARSE_ERROR;
import static codesquad.domain.UserTest.RED;
import static org.slf4j.LoggerFactory.getLogger;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = getLogger(ApiAnswerAcceptanceTest.class);
    
    @Test
    public void show() {
        // 이슈 생성
        IssueBody issueBody = ISSUE_BODY_JSON_PARSE_ERROR;
        String locationIssue = createResource(RED, "/api/issues/", issueBody);
        ResponseEntity<Issue> responseIssue = template().getForEntity(locationIssue, Issue.class);
        
        // 답변 달기
        String answer = "answer";
        String locationAnswer = createResource(RED, String.format("/api/issues/%d/answers", 
                responseIssue.getBody().getId()), answer);
        ResponseEntity<Answer> responseAnswer = template().getForEntity(locationAnswer, Answer.class);
        logger.debug("## show : {}",  responseAnswer.getBody());
        
    }

    @Test
    public void update() {
        // 이슈 생성
        IssueBody issueBody = ISSUE_BODY_JSON_PARSE_ERROR;
        String location = createResource(RED, "/api/issues/", issueBody);
        ResponseEntity<Issue> responseIssue = template().getForEntity(location, Issue.class);

        // 답변달기
        String answer = "answer";
        String answerLocation = createResource(RED, String.format("/api/issues/%d/answers",
                responseIssue.getBody().getId()), answer);
        ResponseEntity<Answer> responseAnswer = template().getForEntity(answerLocation, Answer.class);


        // 답변 수정
        String updateAnswer = "updateAnswer";
        ResponseEntity<Answer> responseEntity = basicAuthTemplate(RED).exchange
                (answerLocation, HttpMethod.PUT, createHttpEntity(updateAnswer), Answer.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update_no_login() {
        // 이슈 생성
        IssueBody issueBody = ISSUE_BODY_JSON_PARSE_ERROR;
        String location = createResource(RED, "/api/issues/", issueBody);
        ResponseEntity<Issue> responseIssue = template().getForEntity(location, Issue.class);

        // 답변달기
        String answer = "answer";
        String answerLocation = createResource(RED, String.format("/api/issues/%d/answers",
                responseIssue.getBody().getId()), answer);
        ResponseEntity<Answer> responseAnswer = template().getForEntity(answerLocation, Answer.class);


        // 답변 수정
        String updateAnswer = "updateAnswer";
        ResponseEntity<Answer> responseEntity = template().exchange
                (answerLocation, HttpMethod.PUT, createHttpEntity(updateAnswer), Answer.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private HttpEntity createHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity(body, headers);
    }
}
