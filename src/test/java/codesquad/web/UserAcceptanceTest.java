package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.User;
import codesquad.domain.UserRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class UserAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

	@Autowired
	private UserRepository userRepository;

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/users/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void create() throws Exception {
		String userId = "testuser";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", userId).addParameter("password", "password").addParameter("name", "자바지기")
				.addParameter("email", "javajigi@slipp.net").build();

		ResponseEntity<String> response = template.postForEntity("/users", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(userRepository.findByUserId(userId));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users"));
	}

	@Test
	public void updateForm_no_login() throws Exception {
		ResponseEntity<String> response = template.getForEntity(String.format("/users/%d/form", loginUser.getId()),
				String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
//		로그인 실패시 HttpStatus.FORBIDDEN이 아니라 /user/login으로 보내게 수정함. (SecurityControllerAdvice class)
		assertTrue(response.getBody().contains("Login Member"));
	}

	@Test
	public void updateForm_login() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate
				.getForEntity(String.format("/users/%d/form", loginUser.getId()), String.class);
		log.debug(loginUser._toUserDto().toString());
		log.debug(response.getBody());
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains(loginUser._toUserDto().getName()), is(true));
	}

	@Test
	public void update_no_login() throws Exception {
		ResponseEntity<String> response = update(template);
//		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains("Login Member"));
	}

	private ResponseEntity<String> update(TestRestTemplate template) throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("_method", "put").addParameter("password", "pass2").addParameter("name", "재성2")
				.addParameter("email", "javajigi@slipp.net").build();

		return template.postForEntity(String.format("/users/%d", loginUser.getId()), request, String.class);
	}

	@Test
	public void update() throws Exception {
		ResponseEntity<String> response = update(basicAuthTemplate);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
	}

	@Test
	public void loginPage() {
		ResponseEntity<String> response = template.getForEntity("/users/login", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void loginSuccess() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", "javajigi").addParameter("password", "testtest").build();
		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
	}

	@Test
	public void loginSuccessMenu() {
		// 로그인한 상태를 테스트할 때 쓰라고 포비가 만들어 놓은 [ basicAuthTemplate ]
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/", String.class);
		String body = response.getBody();
		log.debug("body : {}", response.getBody());
		assertTrue(body.contains("logout") && body.contains("my"));
		assertFalse(body.contains("login") && body.contains("join"));
	}

	@Test
	public void loginfail() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", "javajigi")
				.addParameter("password", "test1").build();
		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		String body = response.getBody();
		assertTrue(body.contains("login") && body.contains("join"));
		assertTrue(body.contains("아이디 또는 비밀번호가 틀립니다."));
	}

	@Test
	public void logout() {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/users/logout", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void updateUserView() {
		ResponseEntity<String> response = basicAuthTemplate
				.getForEntity(String.format("/users/%d/form", loginUser.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		String body = response.getBody();
		log.debug("body : {}", body);
		assertTrue(body.contains("javajigi") && body.contains("자바지기"));
		updateUser();
	}

	public void updateUser() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().put()
				.addParameter("userId", "javajigi")
				.addParameter("password", "testtest")
				.addParameter("name", "상코지기")
				.build();
		log.debug(loginUser.toString());
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/users/%d", loginUser.getId()),
				request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		log.debug(loginUser.toString());
		Optional<User> user = userRepository.findByUserId(loginUser._toUserDto().getUserId());
		assertThat(user.get()._toUserDto().getName(), is("상코지기"));
	}
}
