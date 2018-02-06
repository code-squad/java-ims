package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import codesquad.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.UserRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class UserAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

    @Autowired
    private UserRepository userRepository;

    private User defaultUser;

    @Before
    public void setUp() throws Exception {
        defaultUser = findDefaultUser();
    }

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/users/join", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }

    @Test
    public void create() throws Exception {
        String userId = "testuser";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", userId)
                .addParameter("password", "password")
                .addParameter("name", "자바지기")
                .addParameter("email", "javajigi@slipp.net").build();

        ResponseEntity<String> response = template.postForEntity("/users", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(userRepository.findByUserId(userId));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));
    }

    @Test
    public void updateForm_no_login() throws Exception {
        ResponseEntity<String> response = template.getForEntity(String.format("/users/%d/form", loginUser.getId()),
                String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void updateForm_login() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate
                .getForEntity(String.format("/users/%d/form", loginUser.getId()), String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().contains(loginUser.getName()), is(true));
    }

    @Test
    public void update_no_login() throws Exception {
        ResponseEntity<String> response = update(template);
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    private ResponseEntity<String> update(TestRestTemplate template) throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
                .addParameter("password", "pass2")
                .addParameter("name", "재성2")
                .addParameter("email", "javajigi@slipp.net").build();

        return template.postForEntity(String.format("/users/%d", loginUser.getId()), request, String.class);
    }

    @Test
    public void update() throws Exception {
        ResponseEntity<String> response = update(basicAuthTemplate);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertTrue(response.getHeaders().getLocation().getPath().startsWith("/users"));
    }

    @Test
    public void loginForm() {
        ResponseEntity<String> response = template().getForEntity("/users/login", String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void login() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "javajigi")
                .addParameter("password", "test")
                .build();

        ResponseEntity<String> response = template().postForEntity("/users/login", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));
    }

    @Test
    public void login_fail() {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "test")
                .addParameter("password", "test")
                .build();

        ResponseEntity<String> response = template().postForEntity("/users/login", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains("아이디 또는 비밀번호가 틀렸습니다."));
    }

    @Test
    public void show() {
        ResponseEntity<String> response = basicAuthTemplate()
                .getForEntity(String.format("/users/%d", defaultUser.getId()), String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertTrue(response.getBody().contains(defaultUser.getName()));
    }
}
