package codesquad.web;

import codesquad.dto.AnswerDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiAnswerAcceptanceTest.class);

    @Test
    public void create() throws Exception {
        AnswerDto newAnswer = createAnswerDto(3L);
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/issues/3/answers/", newAnswer, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        AnswerDto dbAnswer = getResource(response.getHeaders().getLocation().toString(), AnswerDto.class);
        log.debug("create test response: {}", dbAnswer.toString());
        assertTrue(dbAnswer.getContents().equals(newAnswer.getContents()));
    }

    private AnswerDto createAnswerDto(long id){
        return new AnswerDto(id, "댓글입니다요");
    }

}
