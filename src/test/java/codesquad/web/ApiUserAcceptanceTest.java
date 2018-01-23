package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import codesquad.domain.User;
import codesquad.dto.UserDto;
import support.test.AcceptanceTest;

public class ApiUserAcceptanceTest extends AcceptanceTest {

	@Test
	public void create() throws Exception {// db에 저장된 유저와 방금 생성한 유저가 같은 유저인지 확인하는 테스트.
		UserDto newUser = createUserDto("testuser1");
		
		ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		 
		String location = response.getHeaders().getLocation().getPath();
		User loginUser = findByUserId(newUser.getUserId());
		UserDto dbUser = basicAuthTemplate(loginUser).getForObject(location, UserDto.class);
		assertThat(dbUser, is(newUser));
	}

	@Test
	public void show_다른_사람() throws Exception {
		UserDto newUser = createUserDto("testuser2");
		String location = createResource("/api/users", newUser);
		// 리소스 = 서버로부터 응답 = forbidden
		ResponseEntity<String> response = getResource(location, findDefaultUser());
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
	}

	private UserDto createUserDto(String userId) {
		return new UserDto(userId, "password", "name");
	}
	
	@Test
	public void update() throws Exception {
		UserDto newUser = createUserDto("testuser3");
		// post 요청을 통해 테스트 데이터 추가.
		ResponseEntity<String> response = template().postForEntity("/api/users", newUser, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
		String location = response.getHeaders().getLocation().getPath();
		// 데이터 비교.
		User loginUser = findByUserId(newUser.getUserId());
		UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2");
		basicAuthTemplate(loginUser).put(location, updateUser);
		// 리소스 = 서버로부터 응답 = 업데이트 한 유저. 
		UserDto dbUser = basicAuthTemplate(loginUser).getForObject(location, UserDto.class);
		assertThat(dbUser, is(updateUser));
	}

	@Test
	public void update_다른_사람() throws Exception {
		UserDto newUser = createUserDto("testuser4");
		String location = createResource("/api/users", newUser);

		UserDto updateUser = new UserDto(newUser.getUserId(), "password", "name2");
		basicAuthTemplate(findDefaultUser()).put(location, updateUser);

		UserDto dbUser = getResource(location, UserDto.class, findByUserId(newUser.getUserId()));
		assertThat(dbUser, is(newUser));
	}
}
