package codesquad.web;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CommentAcceptanceTest extends AcceptanceTest {

    @Test
    public void create() {
        Long issueId = 1L;
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("contents", "댓글 문제가 아닌데요?")
                .build();
        ResponseEntity<String> response = basicAuthTemplate(findDefaultUser()).getForEntity("/issues/" + issueId + "/comments", String.class, request);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
}
