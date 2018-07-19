package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log =  LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Test
    public void issueCreateForm_login() {
        ResponseEntity<String> response = basicAuthTemplate(findDefaultUser()).getForEntity("/issues/form", String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

//    @Test
//    public void issueCreateForm_no_login() {
//        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
//
//        assertThat(response.getStatusCode(), is(HttpStatus.OK));
//    }

//    @Test
//    public void create_no_login() {
//        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
//                .addParameter("subject", "이슈 제목")
//                .addParameter("comment", "이슈 내용").build();
//
//        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
//        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
//
//        response = template.getForEntity("/", String.class);
//        assertThat(response.getBody().contains("이슈 제목"), is(true));
//    }

    public void create_login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "이슈 제목")
                .addParameter("comment", "이슈 내용").build();

        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

        response = template.getForEntity("/", String.class);
        assertThat(response.getBody().contains("이슈 제목"), is(true));
    }

    @Test
    public void show() {
        //create issue
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "이슈 제목")
                .addParameter("comment", "이슈 내용").build();
         basicAuthTemplate(findDefaultUser()).postForEntity("/issues", request, String.class);

        // show issue
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d", 1), String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void updateForm() {
        //create issue
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "이슈 제목")
                .addParameter("comment", "이슈 내용").build();

        TestRestTemplate template = basicAuthTemplate(findDefaultUser());
        template.postForEntity("/issues", request, String.class);

        //update issue
        long issueId = 1;
        ResponseEntity<String> response = template.getForEntity("/issues/"+issueId+"/form", String.class);
        assertThat(response.getBody().contains("Update"), is(true));
    }

    @Test
    public void update() {
        Issue issue = new Issue( "이슈 제목", "이슈 내용");
        TestRestTemplate template = basicAuthTemplate(findDefaultUser());
        template.postForEntity("/issues", issue, String.class);

        Issue updateIssue = new Issue(1L, "수정된 이슈 제목", "수정된 이슈 내용", findDefaultUser());
        template.postForEntity("/issues/1", updateIssue, String.class);

        ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);
        log.debug(response.getBody());
        assertThat(response.getBody().contains("수정된 이슈 제목") ,is(true));
    }

    @Test
    public void delete() {
        Issue issue = new Issue(1L, "이슈 제목", "이슈 내용");
        TestRestTemplate template = basicAuthTemplate(findDefaultUser());
        template.postForEntity("/issues", issue, String.class);

        ResponseEntity<String> response = template.getForEntity("/", String.class);
        assertThat(response.getBody().contains("이슈 제목"), is(true));

        template.delete("/issues/"+issue.getId());
        response = template.getForEntity("/", String.class);
        assertThat(response.getBody().contains("이슈 제목"), is(false));
    }
}
