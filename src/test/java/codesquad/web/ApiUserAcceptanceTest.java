package codesquad.web;

import codesquad.domain.user.User;
import codesquad.dto.UserDto;
import codesquad.validate.ValidationError;
import codesquad.validate.ValidationErrorsResponse;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiUserAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiUserAcceptanceTest.class);

    @Test
    public void create() throws Exception {
        UserDto newUser = createUserDto("testuser1");
        String location = createResource("/api/users", newUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser).isEqualTo(newUser);
    }

    @Test
    public void create_invalid() throws Exception {
        UserDto newUser = new UserDto("a", "a", "a");
        ResponseEntity<ValidationErrorsResponse> response = template.postForEntity("/api/users", newUser, ValidationErrorsResponse.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        for (ValidationError error : response.getBody().getErrors()) {
            log.debug("error : {}", error);
        }
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
        UserDto updateUser = new UserDto(newUser.getUserId(), "password2", "name2");
        basicAuthTemplate(loginUser).put(location, updateUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser).isEqualTo(updateUser);
        softly.assertThat(dbUser.getPassword()).isEqualTo(updateUser.getPassword());
        softly.assertThat(dbUser.getName()).isEqualTo(updateUser.getName());
    }

    @Test
    public void update_다른_사람() throws Exception {
        UserDto newUser = createUserDto("testuser4");
        String location = createResource("/api/users", newUser);

        UserDto updateUser = new UserDto(newUser.getUserId(), "password2", "name2");
        basicAuthTemplate(findDefaultUser()).put(location, updateUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser).isEqualTo(newUser);
        softly.assertThat(dbUser.getPassword()).isEqualTo(newUser.getPassword());
        softly.assertThat(dbUser.getName()).isEqualTo(newUser.getName());
    }
}
