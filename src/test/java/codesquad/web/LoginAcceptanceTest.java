package codesquad.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.w3c.dom.html.HTMLButtonElement;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LoginAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(LoginAcceptanceTest.class);

	@Test
	public void login_form() {
		ResponseEntity<String> response = template.getForEntity("/users/login", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void login_success() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", "javajigi")
				.addParameter("password", "test").build();

		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);

		String location = response.getHeaders().getLocation().getPath();

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(location, is("/"));

		log.debug("bodybody: {}", response.getBody());
	}

	@Test
	public void login_failed() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("userId", "javajigi")
				.addParameter("password", "틀린비밀번호~~").build();

		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/login"));
	}

}
