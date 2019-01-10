package codesquad.web;

import codesquad.domain.Answer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.*;
import support.test.AcceptanceTest;

import static codesquad.domain.UserTest.SANJIGI;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LogManager.getLogger(ApiAnswerAcceptanceTest.class);
    private static final String URL = "/api/issue/1/answers";
    private static final String COMMENT = "나는 댓글이다.";
    private static final String UPDATE_COMMENT = "수정한 댓글입니다.";
    private String location;

    @Before
    public void setup() {
        location = createResource(URL, COMMENT);
        log.debug("나는 왜 안나올까요: {}",location);
    }
    @Test
    public void create() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate().postForEntity(URL, COMMENT, String.class);

        Answer dbAnswer = template().getForObject(location, Answer.class);
        softly.assertThat(dbAnswer).isNotNull();
        softly.assertThat(dbAnswer.getComment()).isEqualTo(COMMENT);
    }


    @Test
    public void update() throws Exception {
        ResponseEntity<String> responseEntity =
                basicAuthTemplate().exchange(location, HttpMethod.PUT, createHttpEntity(UPDATE_COMMENT), String.class);
        log.debug("debuge: {}",responseEntity.getBody());
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update_no_login() throws Exception {
        ResponseEntity<String> responseEntity =
                template().exchange(location, HttpMethod.PUT, createHttpEntity(UPDATE_COMMENT), String.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/login");

    }

    @Test
    public void update_no_others() throws Exception {
        ResponseEntity<String> responseEntity =
                basicAuthTemplate(SANJIGI).exchange(location, HttpMethod.PUT, createHttpEntity(UPDATE_COMMENT), String.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
        log.debug("error message : {}", responseEntity.getBody());
    }


    private HttpEntity createHttpEntity(Object body) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity(body, headers);
    }



}
