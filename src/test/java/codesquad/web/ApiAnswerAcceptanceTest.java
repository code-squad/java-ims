package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.AnswerTest;
import codesquad.domain.UserTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiAnswerAcceptanceTest.class);

    private Answer answer;

    @Before
    public void setUp() throws Exception {
        this.answer = AnswerTest.FIRST_ANSWER;
    }

    @Test
    public void create() {
        ResponseEntity<Answer> response = basicAuthTemplate(UserTest.USER).postForEntity("/api/issues/"+ 1 +"/answers", answer, Answer.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        softly.assertThat(response.getBody().getComment()).isEqualTo(answer.getComment());
//        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/api/issues/\"+ 1 +\"/answers");
    }

    @Test
    public void create_WithOut_Login() {
        ResponseEntity<Answer> response = template().postForEntity("/api/issues/" + 1 + "/answers", answer, Answer.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
