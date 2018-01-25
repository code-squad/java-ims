package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.servlet.http.HttpSession;

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
	public void createForm() throws Exception {// 회원 가입 페이지가 잘 로드 되는지 확인하는 테스트.
		ResponseEntity<String> response = template.getForEntity("/users/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void create() throws Exception {// 회원 데이터가 제대로 생성되는지 테스트.
		String userId = "testuser";
		// 요청 데이터 생성.
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", userId).addParameter("password", "password").addParameter("name", "자바지기")
				.addParameter("email", "javajigi@slipp.net").build();

		ResponseEntity<String> response = template.postForEntity("/users", request, String.class);
		// 응답 코드가 302 redirect 인지 아닌지 확인.
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		// userRepository 에 해당 유저가 생성되어 저장되어 있는지 확인.
		assertNotNull(userRepository.findByUserId(userId));
		// 최종 url 이 "/users" 인지 아닌지 확인.
		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
//		userRepository.delete((long) 3);

	}

	@Test
	public void load_login_form() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/users/loginForm", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void login_success() throws Exception {
		String userId = "javajigi";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", userId).addParameter("password", "password1").build();

		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
		// 세션 id 가져오기 코드 적기.
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void login_fail() throws Exception {
		String userId = "wrong";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", userId).addParameter("password", "test").build();

		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
		String body = response.getBody();
		assertTrue(body.contains("아이디 또는 비밀번호가 다릅니다."));
	}

	@Test
	public void updateForm_no_login() throws Exception {// 로그인 안한 상태에서 회원 정보 수정페이지 로드할때 에러나는지 확인.
		ResponseEntity<String> response = template.getForEntity(String.format("/users/%d/form", loginUser.getId()),
				String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void updateForm_login() throws Exception {// 로그인 했을때 회원 정보 수정 페이지 제대로 로딩되는지 확인.
		ResponseEntity<String> response = basicAuthTemplate
				.getForEntity(String.format("/users/%d/form", loginUser.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains(loginUser.getName()), is(true));
	}

	@Test
	public void update_no_login() throws Exception {
		ResponseEntity<String> response = update(template);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
	}

	private ResponseEntity<String> update(TestRestTemplate template) throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("_method", "put").addParameter("password", "password2").addParameter("name", "재성2")
				.addParameter("email", "javajigi@slipp.net").build();
		return template.postForEntity(String.format("/users/%d", loginUser.getId()), request, String.class);
	}

	@Test
	public void update() throws Exception {
		ResponseEntity<String> response = update(basicAuthTemplate);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
	}
}
