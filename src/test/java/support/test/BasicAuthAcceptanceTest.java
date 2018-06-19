package support.test;

import codesquad.domain.Issue;
import org.junit.Before;
import org.springframework.boot.test.web.client.TestRestTemplate;

import codesquad.domain.User;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

public abstract class BasicAuthAcceptanceTest extends AcceptanceTest {
    protected TestRestTemplate basicAuthTemplate;

    protected User loginUser;

    @Before
    public void setup() {
        loginUser = findDefaultUser();
        basicAuthTemplate = basicAuthTemplate(loginUser);
    }

    protected String createIssueLocation(String subject, String comment) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", comment).build();
        return basicAuthTemplate.postForEntity("/issues", request, String.class).getHeaders().getLocation().toString().split(";")[0];
    }



}
