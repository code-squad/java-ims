package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiUserAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiUserAcceptanceTest.class);
    @Test
    public void create() throws Exception {
        UserDto newUser = createUserDto("reddddddddddddddddd");
        String location = createResource("/api/users", newUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser).isEqualTo(newUser);
    }

    @Test
    public void show_다른_사람() throws Exception {
        UserDto newUser = createUserDto("testuser2");
        String location = createResource("/api/users", newUser);

        ResponseEntity<String> response = getResource(location, findDefaultUser());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private UserDto createUserDto(String userId) {
        return new UserDto(userId, "password", "name");
    }

    @Test
    public void update() throws Exception {
        UserDto newUser = createUserDto("testuser3");
        String location = createResource("/api/users", newUser);

        User loginUser = findByUserId(newUser.getUserId());
        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2");
        basicAuthTemplate(loginUser).put(location, updateUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser).isEqualTo(updateUser);
    }

    @Test
    public void update_다른_사람() throws Exception {
        UserDto newUser = createUserDto("testuser4");
        String location = createResource("/api/users", newUser);

        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2");
        basicAuthTemplate(findDefaultUser()).put(location, updateUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser).isEqualTo(newUser);
    }

    @Test
    public void login_success() {
        UserDto newUser = createUserDto("testUser1");

        ResponseEntity<Void> response = template.postForEntity("/api/users", newUser, Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        ResponseEntity<User> responseEntity = template.postForEntity("/api/users/login", newUser, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/");
    }

    @Test
    public void login_fail_wrong_userId() {
        UserDto newUser = createUserDto("testUser2");
        String location = createResource("/api/users", newUser);
        newUser.setUserId("otherUserId");

        ResponseEntity<User> responseEntity = template.postForEntity("/api/users/login", newUser, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void login_fail_wrong_password() {
        UserDto newUser = createUserDto("testUser3");
        String location = createResource("/api/users", newUser);
        newUser.setPassword("password2");

        ResponseEntity<User> responseEntity = template.postForEntity("/api/users/login", newUser, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }


}
