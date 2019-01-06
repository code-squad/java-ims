package codesquad.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static codesquad.domain.UserTest.JAVAJIGI;

public class LoginAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LogManager.getLogger(LoginAcceptanceTest.class);

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template().getForEntity("/login", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug("body : {}", response.getBody());
    }


    @Test
    public void login() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = getMultiValueMapHttpEntity("test");

        ResponseEntity<String> response = template().postForEntity("/login", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void login_faled() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = getMultiValueMapHttpEntity("asdasdas");

        ResponseEntity<String> response = template().postForEntity("/login", request, String.class);

        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        log.debug(response.getBody());
        softly.assertThat(response.getBody().contains("패스워드가 다릅니다.")).isTrue();
    }

    @Test
    public void logout() throws Exception {
        ResponseEntity<String> response = basicAuthTemplate(JAVAJIGI).getForEntity("/logout",String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    public HttpEntity<MultiValueMap<String, Object>> getMultiValueMapHttpEntity(String password) {
        String userId = "javajigi";
        return HtmlFormDataBuilder
                .urlEncodedForm()
                .addParameter("userId", userId)
                .addParameter("password", password)
                .build();
    }
}
