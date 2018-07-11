package codesquad.web;

import codesquad.domain.Issue;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IssueAcceptanceTest extends AcceptanceTest {

    @Test
    public void issueCreateForm_no_login() {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void create_no_login() {
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
        template.postForEntity("/issues", request, String.class);

        // show issue
        ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d", 1), String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
