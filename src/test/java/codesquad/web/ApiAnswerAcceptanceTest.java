package codesquad.web;

import codesquad.domain.Answer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ApiAnswerAcceptanceTest extends AcceptanceTest{

    private final Logger log = LoggerFactory.getLogger(ApiAnswerAcceptanceTest.class);

    @Test
    public void create_success() {
        String content = "this is answer";
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/issues/1/answers", content, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        Answer dbAnswer = getResource(response.getHeaders().getLocation().toString(), Answer.class, findDefaultUser());
        assertThat(dbAnswer.getContent(), is(content));
    }

    @Test
    public void create_fail_no_login() {
        Answer answer = new Answer("this is answer");
        ResponseEntity<String> response = template.postForEntity("/api/issues/1/answers", answer, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update_success() {

    }

    @Test
    public void update_fail_no_login() {

    }

    @Test
    public void update_fail_other() {

    }

    @Test
    public void delete_success() {

    }

    @Test
    public void delete_fail_no_login() {

    }

    @Test
    public void delete_fail_other() {

    }
}
