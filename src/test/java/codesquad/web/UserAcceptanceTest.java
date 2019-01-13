package codesquad.web;

import codesquad.domain.user.User;
import codesquad.domain.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import javax.persistence.EntityNotFoundException;

import static org.junit.Assert.assertEquals;
import static support.test.Fixture.BRAD;

public class UserAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

    HttpEntity<MultiValueMap<String, Object>> updateRequest;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        updateRequest = HtmlFormDataBuilder.urlEncodedForm().put()
                    .addParameter("_method", "put")
                    .addParameter("password", "password")
                    .addParameter("name", "정현2")
                    .build();
    }

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/users/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() throws Exception {
        String userId = "testuser";
        HttpEntity<MultiValueMap<String, Object>> request = createUserRequest(userId, "password", "브래드", "brad903@naver.com");
        ResponseEntity<String> response = template.postForEntity("/users", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(userRepository.findByUserId(userId)).isNotNull();
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void create_invalid() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = createUserRequest("a", "password", "브래드", "brad903@naver.com");
        ResponseEntity<String> response = template.postForEntity("/users", request, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    private HttpEntity<MultiValueMap<String, Object>> createUserRequest(String userId, String password, String name, String email) {
        return HtmlFormDataBuilder.urlEncodedForm()
                    .addParameter("userId", userId)
                    .addParameter("password", password)
                    .addParameter("name", name)
                    .addParameter("email", email).build();
    }

    @Test
    public void login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "brad903")
                .addParameter("password", "password")
                .build();

        ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void login_fail() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", BRAD.getUserId())
                .addParameter("password", BRAD.getPassword() + "2")
                .build();

        ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().contains("아이디 또는 비밀번호가 다릅니다.")).isTrue();
    }

    @Test
    public void updateForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity(String.format("/users/%d/form", loginUser.getId()),
                String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void updateForm_login() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate
                .getForEntity(String.format("/users/%d/form", loginUser.getId()), String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(response.getBody().contains(loginUser.getName())).isTrue();
    }

    @Test
    public void update_no_login() throws Exception {
        ResponseEntity<String> response = template().postForEntity(String.format("/users/%d", loginUser.getId()), updateRequest, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void update() throws Exception {
        String userId = "updateTestUser";
        HttpEntity<MultiValueMap<String, Object>> request = createUserRequest(userId, "password11", "브래드23", "brad1004@naver.com");
        template.postForEntity("/users", request, String.class);
        User newUser = userRepository.findByUserId(userId).orElseThrow(EntityNotFoundException::new);

        ResponseEntity<String> response = basicAuthTemplate(newUser).postForEntity(String.format("/users/%d", newUser.getId()), updateRequest, String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void logout() {
        ResponseEntity<String> responseEntity = basicAuthTemplate().getForEntity("/users/logout", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> file = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("pic", new ClassPathResource("logback.xml"))
                .addParameter("userId", "mrboo7")
                .addParameter("password", "password")
                .addParameter("name", "브라드").build();
        ResponseEntity<String> result = template.postForEntity("/users", file, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}
