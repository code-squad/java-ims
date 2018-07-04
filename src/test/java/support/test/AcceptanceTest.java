package support.test;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.hamcrest.MatcherAssert;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AcceptanceTest.class);

    private static final String DEFAULT_LOGIN_USER = "javajigi";

    @Autowired
    protected TestRestTemplate template;

    @Autowired
    private UserRepository userRepository;

    public TestRestTemplate template() {
        return template;
    }

    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(findDefaultUser());
    }

    public TestRestTemplate basicAuthTemplate(User loginUser) {
        return template.withBasicAuth(loginUser.getUserId(), loginUser.getPassword());
    }

    protected User findDefaultUser() {
        return findByUserId(DEFAULT_LOGIN_USER);
    }

    protected User findByUserId(String userId) {
        return userRepository.findByUserId(userId).get();
    }

    protected String createResource(String path, Object bodyPayload) {
        ResponseEntity<String> response = template().postForEntity(path, bodyPayload, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        return response.getHeaders().getLocation().getPath();
    }

    protected <T> T getResource(String location, Class<T> responseType, User loginUser) {
        return basicAuthTemplate(loginUser).getForObject(location, responseType);
    }

    protected ResponseEntity<String> getResource(String location, User loginUser) {
        return basicAuthTemplate(loginUser).getForEntity(location, String.class);
    }

    protected HttpEntity makeHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        return new HttpEntity(headers);
    }

    protected String getResponseLocation(ResponseEntity<String> response) {
        return Objects.requireNonNull(response.getHeaders().getLocation()).getPath();
    }

    protected ResponseEntity<String> makeMilestone(String subject) {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("startDate", "2018-07-21T01:59")
                .addParameter("endDate", "2018-10-24T11:58")
                .build();

        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/milestones", request, String.class);

        MatcherAssert.assertThat(responseEntity.getStatusCode(), is(HttpStatus.FOUND));
        return responseEntity;
    }
}
