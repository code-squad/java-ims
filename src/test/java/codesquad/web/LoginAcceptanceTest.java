package codesquad.web;

import codesquad.dto.UserDto;
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

    private HttpEntity<MultiValueMap<String, Object>> makeUserInfo() {
        return HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "javajigi")
                .addParameter("password", "test")
                .build();
    }

    @Test
    public void createForm_success() {
        ResponseEntity<String> response = template.getForEntity("/login", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void login_success() {
        HttpEntity<MultiValueMap<String, Object>> request = makeUserInfo();

        ResponseEntity<String> response
                = template().postForEntity("/login", request, String.class);
        log.debug("response:{}", response);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(response.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void login_mismatch_password() {
        HttpEntity<MultiValueMap<String,Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "javajigi")
                .addParameter("password", "password")
                .build();
        ResponseEntity<String> response
                = template.postForEntity("/login",request,String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    }
}
