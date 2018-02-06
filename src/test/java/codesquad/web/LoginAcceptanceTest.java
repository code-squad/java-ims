package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoginAcceptanceTest extends AcceptanceTest {

    private static final Logger log = LoggerFactory.getLogger(LoginAcceptanceTest.class);

    @Autowired
    UserRepository userRepository;

    public void createUser() throws Exception {
        String userId = "testuser";
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", userId)
                .addParameter("password", "password")
                .addParameter("name", "자바지기")
                .addParameter("email", "javajigi@slipp.net").build();

        ResponseEntity<String> response = template.postForEntity("/users", request, String.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertThat(userRepository.findByUserId(userId).isPresent(), is(true));
        assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
    }

    @Before
    public void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    public void login_success() throws Exception {
        createUser();
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "testuser")
                .addParameter("password", "password")
                .build();

        User loginedUser = new User("testuser", "password", "자바지기");

        ResponseEntity<String> response = basicAuthTemplate(loginedUser).postForEntity("/login", request, String.class);
        assertThat(response.getHeaders().getLocation().getPath(), is("/"));
    }

    @Test
    public void login_fail() throws Exception {
        createUser();
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "john")
                .addParameter("password", "test")
                .addParameter("name", "sehwan")
                .build();

        ResponseEntity<String> response = template.postForEntity("/login", request, String.class);

        log.debug(response.getBody());
        assertThat(response.getBody().contains("아이디 또는 패스워드"), is(true));
    }

    @Test
    public void logout_test() throws Exception {
        User loginedUser = new User("john", "test", "sehwan");
        ResponseEntity<String> response = basicAuthTemplate(loginedUser).getForEntity("/logout", String.class);

        log.debug(response.getBody());
        assertThat(response.getBody().contains("login"), is(true));
    }
}
