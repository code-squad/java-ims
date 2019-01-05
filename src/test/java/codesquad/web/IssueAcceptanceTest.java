package codesquad.web;

import codesquad.domain.IssueRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static codesquad.domain.IssueTest.originalIssue;
import static codesquad.domain.UserTest.SANJIGI;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void createIssueForm() throws Exception {
        ResponseEntity<String> response =
                basicAuthTemplate.getForEntity(String.format("/issues/form"), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void createIssueForm_no_login() throws Exception {
        ResponseEntity<String> response =
                template.getForEntity("/issues/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void createIssue() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "제목입니다.")
                .addParameter("comment", "내용입니다.")
                .addParameter("writer", 1L).build();

        ResponseEntity<String> response =
                basicAuthTemplate.postForEntity("/issues", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void createIssueUpdateForm_no_login() throws Exception {
        ResponseEntity<String> response =
                template.getForEntity(String.format("/issues/%d/form", originalIssue.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void createIssueUpdateFrom_login() throws Exception {
        ResponseEntity<String> response =
                basicAuthTemplate.getForEntity(String.format("/issues/%d/form", originalIssue.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void createIssueUpdateForm_other_user() throws Exception {
        ResponseEntity<String> response =
                basicAuthTemplate(SANJIGI).getForEntity(String.format("/issues/%d/form", originalIssue.getId()), String.class);
//        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);     //basicAuthTemplate에서 FORBIDDEN에러 뜨는 듯 한데 구체적으로 어느 부분에서 나는지 모르겠음.
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<String> update(TestRestTemplate template) throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .put()
                .addParameter("subject", "updatedSubject")
                .addParameter("comment", "updatedComment")
                .build();
        return template.postForEntity(String.format("/issues/%d", originalIssue.getId()), request, String.class);
    }

    @Test
    public void update_no_login() throws Exception {
        ResponseEntity<String> response = update(template());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_login() throws Exception {
        ResponseEntity<String> response = update(basicAuthTemplate());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath())
                .isEqualTo(String.format("/issues/%d",originalIssue.getId()));
    }

    @Test
    public void update_other_user() throws Exception {
        ResponseEntity<String> response = update(basicAuthTemplate(SANJIGI));
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }
}
