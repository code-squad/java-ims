package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.UserTest;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.slf4j.LoggerFactory.getLogger;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = getLogger(IssueAcceptanceTest.class);

    private Issue issue;

    @Autowired
    private IssueRepository issueRepository;

    @Before
    public void setUp() throws Exception {
        Issue issue = new Issue(1L, "new test", "new comment", UserTest.USER, false);
        this.issue = issue;
    }

    @Test
    public void createForm() {
        ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "first subject")
                .addParameter("comment", "new comment").build();

        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(issueRepository.findById(Long.valueOf(1))).isNotNull();
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

//    상세보기 가는 것은 자꾸 FORBIDEN으로  NullPoint로 되어서 문제가 자꾸 발생...
    @Test
    public void show() {
        ResponseEntity<Void> response = template.getForEntity("/issues/1", Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//      softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/issue/show");
    }

    @Test
    public void update() {
        ResponseEntity<String> response = basicAuthTemplate(UserTest.USER)
                .getForEntity("/issues/1/update", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void update_no_login() {
        ResponseEntity<String> response = template.getForEntity("/issues/1/update", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_Other_login() {
        ResponseEntity<String> responseEntity = basicAuthTemplate(UserTest.OTHER_USER)
                .getForEntity("/issues/1/update", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void delete() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .delete()
                .build();
        ResponseEntity<String> response = basicAuthTemplate(UserTest.USER)
                .postForEntity("/issues/1", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        softly.assertThat(response.getHeaders().getLocation().getPath()).startsWith("/");

    }
}
