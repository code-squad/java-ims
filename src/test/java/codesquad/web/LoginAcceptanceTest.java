package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.UserRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class LoginAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(LoginAcceptanceTest.class);

	@Autowired
	private UserRepository userRepository;

	@Test
	public void loginFail() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", "gram").addParameter("password", "1234").build();
		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(userRepository.findByUserId("gram").isPresent(), is(false));
	}
	
	@Test
	public void loginSuccess() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", "javajigi").addParameter("password", "test").build();
		
		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(userRepository.findByUserId("javajigi").isPresent(), is(true));
		
	}
	
}
