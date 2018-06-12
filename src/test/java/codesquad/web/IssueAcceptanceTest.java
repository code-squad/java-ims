package codesquad.web;

import codesquad.domain.IssueRepository;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import javax.annotation.Resource;

public class IssueAcceptanceTest extends AcceptanceTest {

    @Resource(name = "issueRepository")
    private IssueRepository issueRepository;

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().
                addParameter("title", "testIssue").
                addParameter("contents", "testContents").build();

        long beforeCount = issueRepository.count();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(issueRepository.count(), is(beforeCount+1));
    }

    @Test
    public void create_제목_내용_없음() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().
                addParameter("title", "").
                addParameter("contents", "").build();

        long beforeCount = issueRepository.count();
        ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.INTERNAL_SERVER_ERROR));
        assertThat(issueRepository.count(), is(beforeCount));
    }

    @Test
    public void create_no_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().
                addParameter("title", "testIssue2").
                addParameter("contents", "testContents2").build();

        long beforeCount = issueRepository.count();
        ResponseEntity<String> response = template().postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
        assertThat(issueRepository.count(), is(beforeCount));
    }

    @Test
    public void createForm(){
        ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void show(){
        ResponseEntity<String> response = template().getForEntity("/issues/1", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains("test"), is(true));
    }
}
