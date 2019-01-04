package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiLoginAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiLoginAcceptanceTest.class);

    @Test
    public void login_success() {
        UserDto newUser = createUserDto("testUser1");

        ResponseEntity<Void> response = template.postForEntity("/api/users", newUser, Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<User> responseEntity = template.postForEntity("/api/login", newUser, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void login_fail_wrong_userId() {
        UserDto newUser = createUserDto("testUser2");
        String location = createResource("/api/users", newUser);
        newUser.setUserId("otherUserId");

        ResponseEntity<User> responseEntity = template.postForEntity("/api/login", newUser, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void login_fail_wrong_password() {
        UserDto newUser = createUserDto("testUser3");
        String location = createResource("/api/users", newUser);
        newUser.setPassword("password2");

        ResponseEntity<User> responseEntity = template.postForEntity("/api/login", newUser, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    private UserDto createUserDto(String userId) {
        return new UserDto(userId, "password", "name");
    }
}