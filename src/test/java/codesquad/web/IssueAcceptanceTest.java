package codesquad.web;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    private TestRestTemplate template;
    private long issueId = 1;

    @Before
    public void setUp() {
        template = basicAuthTemplate(findDefaultUser());
        createDummyIssue();
    }

    private void createDummyIssue() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "생성된 첫 번째 이슈 제목")
                .addParameter("comment", "생성된 첫 번째 이슈 내용").build();
        template.postForEntity("/issues", request, String.class);

        request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "수정될 두 번째 이슈 제목")
                .addParameter("comment", "수정될 두 번째 이슈 내용").build();
        template.postForEntity("/issues", request, String.class);

        request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "수정 후 삭제될 세 번째 이슈 제목")
                .addParameter("comment", "수정 후 삭제될 세 번째 이슈 내용").build();
        template.postForEntity("/issues", request, String.class);
    }

    @Test
    public void issueCreateForm_login() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void create_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "생성된 네 번째 이슈 제목")
                .addParameter("comment", "생성된 네 번째 이슈 내용").build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

        response = template.getForEntity("/", String.class);
        assertThat(response.getBody().contains("생성된 네 번째 이슈 제목"), is(true));
    }

    @Test
    public void show() {
        ResponseEntity<String> response = super.template.getForEntity(String.format("/issues/%d", issueId), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateForm() {
        issueId = 2L;
        ResponseEntity<String> response = template.postForEntity("/issues/" + issueId + "/form", null, String.class);
        assertThat(response.getBody().contains("Update"), is(true));
    }

    @Test
    public void update() {
        issueId = 2L;

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "수정된 이슈 제목")
                .addParameter("comment", "수정된 이슈 내용").build();
        template.put("/issues/" + issueId, request, String.class);

        ResponseEntity<String> response = template.getForEntity("/issues/" + issueId, String.class);
        log.debug(response.getBody());
        assertThat(response.getBody().contains("수정된 이슈 제목"), is(true));
    }

    @Test
    public void delete() {
        issueId = 3L;

        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "수정된 세 번째 이슈 제목")
                .addParameter("comment", "수정된 세 번째 이슈 내용").build();
        template.put("/issues/" + issueId, request, String.class);

        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getBody().contains("수정된 세 번째 이슈 제목"), is(true));

        template.delete("/issues/" + issueId);
        response = template.getForEntity("/", String.class);
        assertThat(response.getBody().contains("수정된 세 번째 이슈 제목"), is(false));
    }
}
