package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.LabelRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class LabelAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

	@Autowired
	private LabelRepository labelRepository;

	@Test
	public void createForm_no_login() throws Exception {// 이슈등록 페이지가 잘 로드 되는지 테스트.
		ResponseEntity<String> response = template.getForEntity("/labels/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void createForm_login() throws Exception {// 이슈등록 페이지가 잘 로드 되는지 테스트.
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/labels/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		// http 응답 메세지의 location 확인하는 테스트 코드.
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void create_label_no_login() throws Exception {
		String subject = "testSubject";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", subject).build();

		ResponseEntity<String> response = template.postForEntity("/labels", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertEquals(Optional.empty(), labelRepository.findBySubject(subject));
		// 이슈추가 후 이슈목록페이지로 이동.
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void create_label_login() throws Exception {
		String subject = "testSubject";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", subject).build();

		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/labels", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(labelRepository.findBySubject(subject));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
		log.debug("body : {}", response.getBody());
	}
}
