package codesquad.web;

import codesquad.domain.User;
import codesquad.domain.UserTest;
import codesquad.dto.IssueDto;
import codesquad.dto.UserDto;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.slf4j.LoggerFactory.getLogger;

public class ApiUserAcceptanceTest extends AcceptanceTest {
    private static final Logger log = getLogger(ApiUserAcceptanceTest.class);

    private UserDto createUserDto() {
        return new UserDto("testUser", "password", "name");
    }

    @Test
    public void create() throws Exception {                             //createUserDto()는 되고 왜 UserTest.JAVAJI._toDto()는 안되는가..
        String location = createResource_no_login("/api/users", createUserDto());       //자바 지기로 회원 가입
        log.debug("location:{}",location);
        UserDto dbUser = getResource(location, UserDto.class, createUserDto()._toUser());   //회원가입한 유저의 정보를 가져온다.
        log.debug("dbUser:{}", dbUser);
        softly.assertThat(dbUser).isEqualTo(createUserDto());
    }

    @Test
    public void show_다른_사람() throws Exception {
        String location = createResource_no_login("/api/users", createUserDto());
        log.debug("newUser : {}", createUserDto()._toUser());

        ResponseEntity<IssueDto> response = basicAuthTemplate().getForEntity(location, IssueDto.class);
        log.debug("response:{}", response);
        // javajigi가 testuser의 회원정보를 보려고 한다.
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }


    @Test
    public void update() throws Exception {
        String location = createResource_no_login("/api/users", createUserDto());   //testUser회원 생성

        UserDto updateUser = new UserDto(createUserDto().getUserId(), "password2", "name2"); //업데이트할 정보 생성
        ResponseEntity<UserDto> response
                = basicAuthTemplate(findByUserId("testUser")).exchange(location, HttpMethod.PUT,createHttpEntity(updateUser), UserDto.class);//testUser가 업데이트정보 요청
        log.debug("response:{}",response);
        UserDto dbUser = getResource(location, UserDto.class, findByUserId("testUser"));   //testUser가 업데이트한정보 요청
        log.debug("dbUser:{}",dbUser);
        softly.assertThat(dbUser).isEqualTo(updateUser);    // testUser가 업데이트 완료한 정보와 업데이트할 정보가 일치한지 확인
    }

    @Test
    public void update_다른_사람() throws Exception {
        String location = createResource_no_login("/api/users", createUserDto());

        UserDto updateUser = new UserDto(createUserDto().getUserId(), "password", "name2");
        ResponseEntity<UserDto> response =
            basicAuthTemplate().exchange(location,HttpMethod.PUT,createHttpEntity(updateUser),UserDto.class);

        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }

    @Test
    public void update_by_guest_user() {
        String location = createResource_login("/api/users",createUserDto());

        UserDto updateUser = new UserDto(createUserDto().getUserId(),"password","name2");
        ResponseEntity<UserDto> response =
                template().exchange(location,HttpMethod.PUT,createHttpEntity(updateUser),UserDto.class);
        assertThat(response.getStatusCode(), is(HttpStatus.UNAUTHORIZED));

    }
}
