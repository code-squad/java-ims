package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(ApiAnswerAcceptanceTest.class);

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssueFormData();
        ResponseEntity<String> issueResponse = basicAuthTemplate().postForEntity("/issues", request, String.class);
        MatcherAssert.assertThat(issueResponse.getStatusCode(), Matchers.is(HttpStatus.FOUND));
        String issuePath = getResponseLocation(issueResponse);

        Map<String, Object> dataRequest = new LinkedHashMap<>();
        dataRequest.put("content", "test content");

        ResponseEntity<AnswerDto> answerResponse = basicAuthTemplate().postForEntity(issuePath + "/answers", dataRequest, AnswerDto.class);
        assertThat(answerResponse.getStatusCode(), is(HttpStatus.CREATED));
        log.debug("body : {}", answerResponse.getBody());

        assertThat(answerResponse.getBody().getContents(), is("test content"));
        assertTrue(answerResponse.getHeaders().getLocation().getPath().
                startsWith(issuePath + "/answers/"));
    }

    @Test
    public void create_guest() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssueFormData();
        ResponseEntity<String> issueResponse = basicAuthTemplate().postForEntity("/issues", request, String.class);
        MatcherAssert.assertThat(issueResponse.getStatusCode(), Matchers.is(HttpStatus.FOUND));
        String issuePath = getResponseLocation(issueResponse);

        Map<String, Object> dataRequest = new LinkedHashMap<>();
        dataRequest.put("content", "test content");

        ResponseEntity<AnswerDto> answerResponse = template.postForEntity(issuePath + "/answers", dataRequest, AnswerDto.class);
        assertThat(answerResponse.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void delete() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssueFormData();
        ResponseEntity<String> issueResponse = basicAuthTemplate().postForEntity("/issues", request, String.class);
        MatcherAssert.assertThat(issueResponse.getStatusCode(), Matchers.is(HttpStatus.FOUND));
        String issuePath = getResponseLocation(issueResponse);

        Map<String, Object> dataRequest = new LinkedHashMap<>();
        dataRequest.put("content", "test content");

        ResponseEntity<AnswerDto> answerResponse = basicAuthTemplate().postForEntity(issuePath + "/answers", dataRequest, AnswerDto.class);
        assertThat(answerResponse.getStatusCode(), is(HttpStatus.CREATED));
        log.debug("body : {}", answerResponse.getBody());

        ResponseEntity<String> deleteResponse = basicAuthTemplate().exchange(answerResponse.getHeaders().getLocation().getPath(),
                HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertThat(deleteResponse.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void delete_guest() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssueFormData();
        ResponseEntity<String> issueResponse = basicAuthTemplate().postForEntity("/issues", request, String.class);
        MatcherAssert.assertThat(issueResponse.getStatusCode(), Matchers.is(HttpStatus.FOUND));
        String issuePath = getResponseLocation(issueResponse);

        Map<String, Object> dataRequest = new LinkedHashMap<>();
        dataRequest.put("content", "test content");

        ResponseEntity<AnswerDto> answerResponse = basicAuthTemplate().postForEntity(issuePath + "/answers", dataRequest, AnswerDto.class);
        assertThat(answerResponse.getStatusCode(), is(HttpStatus.CREATED));
        log.debug("body : {}", answerResponse.getBody());

        ResponseEntity<String> deleteResponse = template.exchange(answerResponse.getHeaders().getLocation().getPath(),
                HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertThat(deleteResponse.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void delete_another() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssueFormData();
        ResponseEntity<String> issueResponse = basicAuthTemplate().postForEntity("/issues", request, String.class);
        MatcherAssert.assertThat(issueResponse.getStatusCode(), Matchers.is(HttpStatus.FOUND));
        String issuePath = getResponseLocation(issueResponse);

        Map<String, Object> dataRequest = new LinkedHashMap<>();
        dataRequest.put("content", "test content");

        ResponseEntity<AnswerDto> answerResponse = basicAuthTemplate().postForEntity(issuePath + "/answers", dataRequest, AnswerDto.class);
        assertThat(answerResponse.getStatusCode(), is(HttpStatus.CREATED));
        log.debug("body : {}", answerResponse.getBody());

        User newUser = new User("testid", "testpassword", "testname");
        ResponseEntity<String> deleteResponse = basicAuthTemplate(newUser).exchange(answerResponse.getHeaders().getLocation().getPath(),
                HttpMethod.DELETE, HttpEntity.EMPTY, String.class);
        assertThat(deleteResponse.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void list() {
        HttpEntity<MultiValueMap<String, Object>> request = makeIssueFormData();
        ResponseEntity<String> issueResponse = basicAuthTemplate().postForEntity("/issues", request, String.class);
        MatcherAssert.assertThat(issueResponse.getStatusCode(), Matchers.is(HttpStatus.FOUND));
        String issuePath = getResponseLocation(issueResponse);

        Map<String, Object> dataRequest = new LinkedHashMap<>();
        dataRequest.put("content", "test content1");
        ResponseEntity<AnswerDto> answerResponse = basicAuthTemplate().postForEntity(issuePath + "/answers", dataRequest, AnswerDto.class);
        assertThat(answerResponse.getStatusCode(), is(HttpStatus.CREATED));

        dataRequest.put("content", "test content2");
        answerResponse = basicAuthTemplate().postForEntity(issuePath + "/answers", dataRequest, AnswerDto.class);
        assertThat(answerResponse.getStatusCode(), is(HttpStatus.CREATED));

        dataRequest.put("content", "test content3");
        answerResponse = basicAuthTemplate().postForEntity(issuePath + "/answers", dataRequest, AnswerDto.class);
        assertThat(answerResponse.getStatusCode(), is(HttpStatus.CREATED));
        log.debug("body : {}", answerResponse.getBody());

        ResponseEntity<String> listResponse = template.getForEntity(issuePath + "/answers", String.class);
        assertThat(listResponse.getStatusCode(), is(HttpStatus.OK));

        log.debug("answers : {}", listResponse.getBody());

        assertTrue(listResponse.getBody().contains("test content1"));
        assertTrue(listResponse.getBody().contains("test content2"));
        assertTrue(listResponse.getBody().contains("test content3"));
    }
}