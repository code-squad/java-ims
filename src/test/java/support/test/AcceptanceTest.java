package support.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.springframework.util.MultiValueMap;

import java.net.URI;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {
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
        return createResource(template(), path, bodyPayload);
    }

    protected String createResource(TestRestTemplate template, String path, Object bodyPayload) {
        ResponseEntity<String> response = template.postForEntity(path, bodyPayload, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        return response.getHeaders().getLocation().getPath();
    }
    
    protected <T> T getResource(String location, Class<T> responseType, User loginUser) {
        return basicAuthTemplate(loginUser).getForObject(location, responseType);
    }

    protected ResponseEntity<String> getResource(String location, User loginUser) {
        return basicAuthTemplate(loginUser).getForEntity(location, String.class);
    }

    protected String getPath(ResponseEntity<?> response) {
        return response.getHeaders().getLocation().getPath();
    }

    protected ResponseEntity<String> requestPost(TestRestTemplate template, String uri, HttpEntity<MultiValueMap<String, Object>> request) {
        return template.postForEntity(uri, request, String.class);
    }

    protected ResponseEntity<String> requestPost(String uri, HttpEntity<MultiValueMap<String, Object>> request) {
        return requestPost(template(), uri, request);
    }

    protected ResponseEntity<String> requestGet(String uri) {
        return requestGet(template(), uri);
    }

    protected ResponseEntity<String> requestGet(TestRestTemplate template, String uri) {
        return template.getForEntity(uri, String.class);
    }

    protected <T> ResponseEntity<T> requestGet(TestRestTemplate template, String path, Class<T> responseType) {
        return template.getForEntity(path, responseType);
    }
}
