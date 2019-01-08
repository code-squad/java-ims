package codesquad.web;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.*;
import support.test.AcceptanceTest;
import support.test.AnswerFixture;
import support.test.UserFixture;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {

    private static final Logger logger = getLogger(ApiAnswerAcceptanceTest.class);

    @Test
    public void 로그인X_댓글작성_실패() {
        ResponseEntity<Void> responseEntity = template
                .postForEntity("/api/issues/1/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 로그인O_댓글작성_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        logger.debug("Location : {}", responseEntity.getHeaders().getLocation().getPath());

    }

    @Test
    public void 로그인X_댓글수정_실패() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        String location = responseEntity.getHeaders().getLocation().getPath();

        ResponseEntity<String> response = exchangeResource(template, location, HttpMethod.PUT, AnswerFixture.SUCCESS_ANSWER_DOBY);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
    @Test
    public void 로그인O_본인X_댓글수정_실패() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        String location = responseEntity.getHeaders().getLocation().getPath();

        ResponseEntity<String> response = exchangeResource(basicAuthTemplate(), location, HttpMethod.PUT, AnswerFixture.SUCCESS_ANSWER_DOBY);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void 로그인O_본인O_댓글수정_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        String location = responseEntity.getHeaders().getLocation().getPath();
        logger.debug("Location : {}", location);

        ResponseEntity<String> response = exchangeResource(basicAuthTemplate(UserFixture.DOBY)
                , location, HttpMethod.PUT, AnswerFixture.SUCCESS_ANSWER_DOBY);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void 로그인X_댓글삭제_실패() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        String location = responseEntity.getHeaders().getLocation().getPath();

        ResponseEntity<String> response = exchangeResource(template, location, HttpMethod.DELETE, null);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 로그인O_본인X_댓글삭제_실패() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        String location = responseEntity.getHeaders().getLocation().getPath();

        ResponseEntity<String> response = exchangeResource(basicAuthTemplate(), location, HttpMethod.DELETE, null);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void 로그인O_본인O_댓글삭제_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/issues/1/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        String location = responseEntity.getHeaders().getLocation().getPath();

        ResponseEntity<String> response = exchangeResource(basicAuthTemplate(UserFixture.DOBY), location, HttpMethod.DELETE, null);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
