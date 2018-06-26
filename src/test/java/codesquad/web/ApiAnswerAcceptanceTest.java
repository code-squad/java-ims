package codesquad.web;

import codesquad.domain.Answer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
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
        String content = "this is answer";
        ResponseEntity<String> response = template.postForEntity("/api/issues/1/answers", content, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update_success() {
        String content = "this is answer";
        String location = loginCreateResource("/api/issues/1/answers", content);
        log.debug("location : {}", location);
        String updateContent = "helllooo";
        basicAuthTemplate().put(location, updateContent);
        Answer dbAnswer = getResource(location, Answer.class, findDefaultUser());
        assertThat(dbAnswer.getContent(), is(updateContent));
    }

    @Test
    public void update_fail_no_login() {
        String content = "this is answer";
        String location = loginCreateResource("/api/issues/1/answers", content);
        String updateContent = "helllooo";
        template().put(location, updateContent);
        Answer dbAnswer = getResource(location, Answer.class, findDefaultUser());
        assertThat(dbAnswer.getContent(), is("this is answer"));
    }

    @Test
    public void update_fail_other() {
        String content = "this is answer";
        String location = loginCreateResource("/api/issues/1/answers", content);
        String updateContent = "helllooo";
        basicAuthTemplate(findByUserId("sanjigi")).put(location, updateContent);
        Answer dbAnswer = getResource(location, Answer.class, findDefaultUser());
        assertThat(dbAnswer.getContent(), is("this is answer"));
    }

    @Test
    public void delete_success() {
        String content = "this is answer";
        String location = loginCreateResource("/api/issues/1/answers", content);
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);

        ResponseEntity<String> response = basicAuthTemplate().exchange(location, HttpMethod.DELETE, entity, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    public void delete_fail_no_login() {
        String content = "this is answer";
        String location = loginCreateResource("/api/issues/1/answers", content);
        template().delete(location);
        Answer dbAnswer = getResource(location, Answer.class, findDefaultUser());
        assertThat(dbAnswer.getContent(), is("this is answer"));
    }

    @Test
    public void delete_fail_other() {
        String content = "this is answer";
        String location = loginCreateResource("/api/issues/1/answers", content);
        template().delete(location);
        Answer dbAnswer = getResource(location, Answer.class, findDefaultUser());
        assertThat(dbAnswer.getContent(), is("this is answer"));
    }
}
