package codesquad.web;

import codesquad.domain.UserRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.slf4j.LoggerFactory.getLogger;

public class LoginAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = getLogger(LoginAcceptanceTest.class);

    private UserRepository userRepository;

    @Test
    public void loginForm() {
        ResponseEntity<String> response = template().getForEntity("/login", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/login");
    }

    @Test
    public void login() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "kuro")
                .addParameter("password", "eogks369 ")
                .build();
        ResponseEntity<String> response = template().postForEntity("/login", request, String.class);

        log.debug("login ~~~~~~: {}" , response.getBody());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }
}
