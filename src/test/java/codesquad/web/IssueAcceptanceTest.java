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

import codesquad.domain.IssueRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

	@Autowired
	private IssueRepository issueRepository;

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void create() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "제목제목").addParameter("comment", "내용내용").build();
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(issueRepository.findById(1L).isPresent(), is(true));
		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
	}

	@Test
	public void create_fail_유효성검사통과실패() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "제").addParameter("comment", "내용내용").build();
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains("이슈의 제목은"), is(true));
	}

	@Test
	public void showIssue() {
		ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void updateForm_noLogin() {
		ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d/form", loginUser.getId()),
				String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void updateForm_login() {
		ResponseEntity<String> response = basicAuthTemplate()
				.getForEntity(String.format("/issues/%d/form", loginUser.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void update_noLogin() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.put().addParameter("subject", "바뀐제목제목").addParameter("comment", "바뀐내용내용").build();
		ResponseEntity<String> response = template.postForEntity(String.format("/issues/%d", 1L), request ,String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void update_login() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.put().addParameter("subject", "바뀐제목제목").addParameter("comment", "바뀐내용내용").build();
		ResponseEntity<String> response = basicAuthTemplate().postForEntity(String.format("/issues/%d", 1L), request ,String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is(String.format("/issues/%d", 1L)));
	}
	
	@Test
	public void delete_noLogin() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().delete().build();
		ResponseEntity<String> response = template.postForEntity(String.format("/issues/%d", 1L), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}
	
	@Test
	public void delete_login() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().addParameter("_method", "delete").build();
		ResponseEntity<String> response = basicAuthTemplate().postForEntity(String.format("/issues/%d", 1L), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(issueRepository.findById(1L).isPresent(), is(false));
	}

}
