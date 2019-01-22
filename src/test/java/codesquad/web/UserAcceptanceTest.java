package codesquad.web;

import codesquad.repository.UserRepository;
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

public class UserAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

    @Autowired
    private UserRepository userRepository;

    private HtmlFormDataBuilder makeUser() {
        return HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "testUser")
                .addParameter("password", "password")
                .addParameter("name", "자바지기")
                .addParameter("email", "javajigi@slipp.net");
    }

    private ResponseEntity<String> update(TestRestTemplate template) throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
//                .addParameter("_method", "put")
                .addParameter("password", "pass2")
                .addParameter("name", "재성2")
                .addParameter("email", "javajigi@slipp.net").put().build();

        return template.postForEntity(String.format("/users/%d", findByUserId("javajigi").getId()), request, String.class);
    }

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/users/join", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = makeUser().build();

        ResponseEntity<String> response = template.postForEntity("/users", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(userRepository.findByUserId("testUser")).isNotNull();
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void updateForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity(String.format("/users/%d/form", loginUser.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void updateForm_login() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate()
                .getForEntity(String.format("/users/%d", loginUser.getId()), String.class);
        log.debug("loginUser : {}", loginUser);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().contains(loginUser._toUserDto().getName())).isTrue();
    }

    @Test
    public void update_no_login() throws Exception {
        ResponseEntity<String> response = update(template);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void update() throws Exception {
        ResponseEntity<String> response = update(basicAuthTemplate);
        log.debug("response:{}", response);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }
}
