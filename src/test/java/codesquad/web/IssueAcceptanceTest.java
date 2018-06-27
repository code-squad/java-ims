package codesquad.web;

import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.util.Objects;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("{}", response.getBody());
    }

    @Test
    public void create() {
        Long id = 1L;

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test subject")
                .addParameter("comment", "test comment")
                .build();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(issueRepository.findById(id));
        assertTrue(Objects.requireNonNull(responseEntity.getHeaders().getLocation()).getPath().startsWith(String.format("/issues/%d", id)));
    }

    @Test
    public void create_fail() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("comment", "test comment")
                .build();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void create_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test subject")
                .addParameter("comment", "test comment")
                .build();

        ResponseEntity<String> responseEntity = template.postForEntity("/issues", request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FORBIDDEN));

    }

    @Test
    public void list() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test subject1")
                .addParameter("comment", "test comment1")
                .build();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));

        request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test subject2")
                .addParameter("comment", "test comment2")
                .build();

        responseEntity = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));

        responseEntity = basicAuthTemplate().getForEntity("/", String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));

        log.debug("body : {}", responseEntity.getBody());
        assertTrue(responseEntity.getBody().contains("test subject1"));
        assertTrue(responseEntity.getBody().contains("test subject2"));
        assertTrue(responseEntity.getBody().contains("javajigi"));
    }

    @Test
    public void show() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test subject")
                .addParameter("comment", "test comment")
                .build();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));

        String path = responseEntity.getHeaders().getLocation().getPath();
        log.debug("path : {}", path);

        // TODO Issue 객체를 받아서 테스트 하고 싶었지만 계속 실패해서 일단 String

        ResponseEntity<String> response = basicAuthTemplate().getForEntity(String.format(path), String.class);

        log.debug("response : {}", response.getBody());

        assertTrue(response.getBody().contains("test subject"));
        assertTrue(response.getBody().contains("test comment"));
        assertTrue(response.getBody().contains("javajigi"));
    }

    @Test
    public void updateForm() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test subject")
                .addParameter("comment", "test comment")
                .build();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));

        ResponseEntity<String> response = template.getForEntity("/issues/1/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("{}", response.getBody());
    }

    @Test
    public void update() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "test subject")
                .addParameter("comment", "test comment")
                .build();

        ResponseEntity<IssueDto> responseEntity = basicAuthTemplate().postForEntity("/issues", request, IssueDto.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));

        request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
                .addParameter("subject", "modified subject")
                .addParameter("comment", "modified comment")
                .build();

        responseEntity = basicAuthTemplate().postForEntity("/issues/1", request, IssueDto.class);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));
    }
}
