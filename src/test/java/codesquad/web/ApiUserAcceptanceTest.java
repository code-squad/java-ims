package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.UserDto;
import codesquad.security.HttpSessionUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;
import support.test.UserFixture;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

public class ApiUserAcceptanceTest extends AcceptanceTest {

    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = getLogger(ApiUserAcceptanceTest.class);

    @Test
    public void create() throws Exception {
        UserDto newUser = createUserDto("testuser1");
        /* 1. 회원가입 */
        String location = createResource("/api/user", newUser);

        /* 2. 회원정보 가져와서 비교! */
        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser).isEqualTo(newUser);
    }

    @Test
    public void show_다른_사람() throws Exception {
        UserDto newUser = createUserDto("testuser2");
        /* 1. 회원가입 */
        String location = createResource("/api/user", newUser);

        /* 2. 자바지기로 로그인해서 테스트유저 정보 접근 */
        ResponseEntity<String> response = getResource(location, findDefaultUser());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    private UserDto createUserDto(String userId) {
        return new UserDto(userId, "password", "name");
    }

    @Test
    public void update() {
        UserDto newUser = createUserDto("testuser3");
        /* 1. 회원가입 */
        String location = createResource("/api/user", newUser);

        /* 2. 회원가입한 테스트유저의 정보를 DB에서 가져오기 */
        User loginUser = findByUserId(newUser.getUserId());

        /* 3. 테스트 유저의 정보 변경 */
        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2");

        /* 4. 테스트유저로 로그인해서, 테스트 유저의 정보를 변경 */
        basicAuthTemplate(loginUser).put(location, updateUser);

        /* 5. 회원정보를 DB에서 가져와서 비교 */
        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser).isEqualTo(updateUser);
    }

    @Test
    public void update_다른_사람() {
        UserDto newUser = createUserDto("testuser4");
        String location = createResource("/api/user", newUser);

        UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2");
        basicAuthTemplate(findDefaultUser()).put(location, updateUser);

        UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
        softly.assertThat(dbUser).isEqualTo(newUser);
    }

    @Test
    public void update_같은_사람_패스워드6자리미만() throws Exception {
        UserDto newUser = createUserDto("testuser4");
        String location = createResource("/api/user", newUser);
        logger.debug("Location : {}", location);

        UserDto updateUser = new UserDto(newUser.getUserId(), "pass", "name2");
        ResponseEntity<Void> responseEntity = basicAuthTemplate(newUser._toUser())
                .exchange(location, HttpMethod.PUT, new HttpEntity(updateUser), Void.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 로그인_아이디_잘못입력_실패_Test() {
        UserDto userDto = createUserDto("testId");
        /* 1. 회원가입! */
        ResponseEntity<Void> response = template.postForEntity("/api/user", userDto, Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        userDto.setUserId("NotNot");

        /* 2. 로그인 */
        ResponseEntity<User> responseEntity = template.postForEntity("/api/user/login", userDto, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 로그인_패스워드_잘못입력_실패_Test() {
        UserDto userDto = createUserDto("testId");
        /* 1. 회원가입! */
        ResponseEntity<Void> response = template.postForEntity("/api/user", userDto, Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        userDto.setPassword("NotNotNot");

        /* 2. 로그인 */
        ResponseEntity<User> responseEntity = template.postForEntity("/api/user/login", userDto, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 로그인_성공_Test() {
        UserDto userDto = createUserDto("testId");
        /* 1. 회원가입! */
        ResponseEntity<Void> response = template.postForEntity("/api/user", userDto, Void.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        /* 2. 로그인 */
        ResponseEntity<User> responseEntity = template.postForEntity("/api/user/login", userDto, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).isEqualTo("/");
    }
}
