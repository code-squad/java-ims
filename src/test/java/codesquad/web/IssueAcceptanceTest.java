package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import javax.xml.ws.Response;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

	@Autowired
	private IssueRepository issueRepository;

	@Test
	public void createForm_loginUser() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void createForm_no_loginUser() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void create_loginUser() throws Exception {
		ResponseEntity<String> response = createIssue(basicAuthTemplate);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/issues"));
		assertThat(issueRepository.findOne(1L).getSubject(), is("testSubject"));
	}

	@Test
	public void create_no_loginUser() throws Exception {
		ResponseEntity<String> response = createIssue(template);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void showAll() {
		createIssue(basicAuthTemplate);
		ResponseEntity<String> response = template.getForEntity("/issues", String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains("testSubject"));
	}

	@Test
	public void show() {
		createIssue(basicAuthTemplate);
		ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains("testSubject"));
	}

	private ResponseEntity<String> createIssue(TestRestTemplate template) {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "testSubject")
				.addParameter("comment", "testComment").build();

		return template.postForEntity("/issues", request, String.class);
	}

	private ResponseEntity<String> updateIssue(TestRestTemplate template) {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "바꾼다Subject")
				.addParameter("comment", "바꾼다Comment")
				.put()
				.build();
		return template.postForEntity("/issues/1", request, String.class);
	}

	@Test
	public void update_owner() {
		createIssue(basicAuthTemplate);
		ResponseEntity<String> response = updateIssue(basicAuthTemplate);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
	}

	@Test
	public void update_not_owner() {
		createIssue(basicAuthTemplate);

		User anotherUser = new User("userId", "password", "name");
		ResponseEntity<String> response = updateIssue(basicAuthTemplate(anotherUser));

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void update_no_login() {
		createIssue(basicAuthTemplate);
		ResponseEntity<String> response = updateIssue(template);

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void delete_owner() {
		createIssue(basicAuthTemplate);

		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().delete().build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues/1", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
		assertNull(issueRepository.findOne(1L));
	}

	@Test
	public void delete_not_owner1() {
		createIssue(basicAuthTemplate);

		User anotherUser = new User("userId", "password", "name");

		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().delete().build();
		ResponseEntity<String> response = basicAuthTemplate(anotherUser).postForEntity("/issues/1", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
		assertNotNull(issueRepository.findOne(1L));
	}

	@Test
	public void delete_no_login() {
		createIssue(basicAuthTemplate);

		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().delete().build();
		ResponseEntity<String> response = template.postForEntity("/issues/1", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
		assertNotNull(issueRepository.findOne(1L));
	}


}
