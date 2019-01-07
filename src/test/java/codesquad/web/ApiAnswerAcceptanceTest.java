package codesquad.web;

import org.junit.Test;
import org.springframework.http.*;
import support.test.AcceptanceTest;
import support.test.AnswerFixture;
import support.test.BasicAuthAcceptanceTest;
import support.test.UserFixture;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {

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
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).contains("/api/answer/");
    }

    @Test
    public void 로그인X_댓글수정_실패() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        String location = responseEntity.getHeaders().getLocation().getPath();

        responseEntity = exchangeResource(template, location, HttpMethod.PUT, AnswerFixture.SUCCESS_ANSWER_DOBY);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
    @Test
    public void 로그인O_본인X_댓글수정_실패() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        String location = responseEntity.getHeaders().getLocation().getPath();

        responseEntity = exchangeResource(basicAuthTemplate(), location, HttpMethod.PUT, AnswerFixture.SUCCESS_ANSWER_DOBY);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 로그인O_본인O_댓글수정_성공() {
        ResponseEntity<Void> responseEntity = basicAuthTemplate(UserFixture.DOBY)
                .postForEntity("/api/answers", AnswerFixture.SUCCESS_ANSWER_DOBY, Void.class);
        String location = responseEntity.getHeaders().getLocation().getPath();

        responseEntity = exchangeResource(template, location, HttpMethod.PUT, AnswerFixture.SUCCESS_ANSWER_DOBY);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 로그인X_댓글삭제_실패() {

    }

    @Test
    public void 로그인O_본인X_댓글삭제_실패() {

    }

    @Test
    public void 로그인O_본인O_댓글삭제_성공() {

    }
}
