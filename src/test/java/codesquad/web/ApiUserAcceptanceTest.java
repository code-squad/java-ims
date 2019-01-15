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
    public void create() {
        ResponseEntity responseEntity = createUserResponse("testUser-1", "password", "testName", template);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void 회원가입_중복아이디존재_실패() {
        ResponseEntity responseEntity = createUserResponse("javajigi", "password", "testName", template);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void show_다른_사람() throws Exception {
        ResponseEntity responseEntity = createUserResponse("testUser-2", "password", "testName", template);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        String location = responseEntity.getHeaders().getLocation().getPath();
        logger.debug("Location : {}", location);

        /* 2. 자바지기로 로그인해서 테스트유저 정보 접근 */
        ResponseEntity<String> response = getResource(location, findDefaultUser());
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update() {
        /* 1. 회원가입 */
        String location = createUserResponse("testUser-3", "password", "testName", template)
                .getHeaders().getLocation().getPath();

        /* 2. 회원가입한 테스트유저의 정보를 DB에서 가져오기 */
        User loginUser = findByUserId("testUser-3");

        /* 3. 테스트유저로 로그인해서, 테스트 유저의 정보를 변경 */
        createUserResponse("testUser-3", "password", "updatedName", basicAuthTemplate(loginUser));

        /* 4. 회원정보를 DB에서 가져와서 비교 */
        UserDto dbUser = getResource(location, UserDto.class, findByUserId(loginUser.getUserId()));
        softly.assertThat(loginUser.getName()).isEqualTo(dbUser.getName());
    }

    @Test
    public void update_다른_사람() {
        /* 1. 회원가입 */
        String location = createUserResponse("testUser-4", "password", "testName", template)
                .getHeaders().getLocation().getPath();

        /* 2. 회원가입한 테스트유저의 정보를 DB에서 가져오기 */
        User loginUser = findByUserId("testUser-4");

        /* 3. 테스트유저로 로그인해서, 테스트 유저의 정보를 변경 */
        ResponseEntity responseEntity =
                createUserResponse("testUser-4", "password", "updatedName", basicAuthTemplate(UserFixture.JAVAJIGI));
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_같은_사람_패스워드6자리미만() {
        /* 1. 회원가입 */
        String location = createUserResponse("testUser-5", "password", "testName", template)
                .getHeaders().getLocation().getPath();

        /* 2. 회원가입한 테스트유저의 정보를 DB에서 가져오기 */
        User loginUser = findByUserId("testUser-5");

        /* 3. 테스트유저로 로그인해서, 테스트 유저의 정보를 변경 */
        ResponseEntity responseEntity =
                createUserResponse("testUser-5", "p", "testName", basicAuthTemplate(loginUser));
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }
    @Test
    public void 로그인_아이디_잘못입력_실패_Test() {
        /* 1. 회원가입 */
        createUserResponse("testUser-6", "password", "testName", template);
        UserDto userDto = new UserDto("failIdInput", "password");

        /* 2. 로그인 */
        ResponseEntity<User> responseEntity = template.postForEntity("/api/user/login", userDto, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 로그인_패스워드_잘못입력_실패_Test() {
        /* 1. 회원가입 */
        createUserResponse("testUser-7", "password", "testName", template);
        UserDto userDto = new UserDto("testUser-7", "passwordError");

        /* 2. 로그인 */
        ResponseEntity<User> responseEntity = template.postForEntity("/api/user/login", userDto, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void 로그인_성공_Test() {
        /* 1. 회원가입 */
        createUserResponse("testUser-8", "password", "testName", template);
        UserDto userDto = new UserDto("testUser-8", "password");

        /* 2. 로그인 */
        ResponseEntity<User> responseEntity = template.postForEntity("/api/user/login", userDto, User.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.ACCEPTED);
    }
}
