package codesquad.web;

import codesquad.domain.IssueRepository;
import org.junit.After;
import org.junit.Before;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

	@Autowired
	private IssueRepository issueRepository;

	private static final String ISSUE_SUBJECT = "이슈주제";
	private static final String ISSUE_COMMENT = "이슈코멘트";

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body: {}", response.getBody());
	}

	@Test
	public void createForm_not_login() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	private ResponseEntity<String> create(TestRestTemplate template) {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", ISSUE_SUBJECT)
				.addParameter("comment", ISSUE_COMMENT).build();

		return template.postForEntity("/issues", request, String.class);
	}

	@Test
	public void create() throws Exception {
		ResponseEntity<String> response = create(basicAuthTemplate());

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(issueRepository.findOne(Long.valueOf(1)));
		assertThat(response.getHeaders().getLocation().getPath(), is("/issues"));
	}

	@Test
	public void create_not_login() throws Exception {
		ResponseEntity<String> response = create(template);

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void create_subject_null() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("comment", "이슈코멘트").build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_REQUIRED));
	}

	@Test
	public void create_comment_null() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "이슈주제").build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_REQUIRED));
	}

	@Test
	public void list() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/issues", String.class);
		log.debug("body: {}", response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(ISSUE_COMMENT));
		assertTrue(response.getBody().contains(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))));
	}

	@Test
	public void show() throws Exception {
		ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d", 1), String.class);
		log.debug("body: {}", response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(ISSUE_COMMENT));
		assertTrue(response.getBody().contains(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))));
	}

	@Test
	public void updateForm_no_login() throws Exception {
		ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d/form", 1), String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void updateForm_login() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate().getForEntity(String.format("/issues/%d/form", 1), String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(ISSUE_SUBJECT));
		assertTrue(response.getBody().contains(ISSUE_COMMENT));
	}

	private ResponseEntity<String> update(TestRestTemplate template) throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("_method", "put")
				.addParameter("subject", ISSUE_SUBJECT)
				.addParameter("comment", ISSUE_COMMENT).build();

		return template.postForEntity(String.format("/issues/%d", 1), request, String.class);
	}

	@Test
	public void update_no_login() throws Exception {
		ResponseEntity<String> response = update(template);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void update() throws Exception {
		ResponseEntity<String> response = update(basicAuthTemplate());
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/issues"));
	}
}
