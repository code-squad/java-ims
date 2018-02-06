package codesquad.web;

import codesquad.domain.IssueRepository;
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
import static org.junit.Assert.*;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

	@Autowired
	private IssueRepository issueRepository;

	private static final String ISSUE_SUBJECT = "이슈주제";
	private static final String ISSUE_COMMENT = "이슈코멘트";

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate().getForEntity("/issues/form", String.class);
		log.debug("body: {}", response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
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
		assertNotNull(issueRepository.findOne(Long.valueOf(2)));
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
				.addParameter("comment", ISSUE_COMMENT).build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_REQUIRED));
	}

	@Test
	public void create_comment_null() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", ISSUE_SUBJECT).build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_REQUIRED));
	}

	@Test
	public void list() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/issues", String.class);
		log.debug("body: {}", response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(ISSUE_SUBJECT));
		assertTrue(response.getBody().contains(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))));
	}

	@Test
	public void show() throws Exception {
		ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d", 1), String.class);
		log.debug("body: {}", response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(ISSUE_SUBJECT));
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

	private ResponseEntity<String> update(TestRestTemplate template, long id) throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("_method", "put")
				.addParameter("subject", "새로운 이슈주제")
				.addParameter("comment", "새로운 이슈코멘트").build();

		return template.postForEntity(String.format("/issues/%d", id), request, String.class);
	}

	@Test
	public void update_no_login() throws Exception {
		ResponseEntity<String> response = update(template, 1);

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void update() throws Exception {
		long id = 1;
		ResponseEntity<String> response = update(basicAuthTemplate(), id);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/issues"));
		assertThat(issueRepository.findOne(Long.valueOf(id)).getSubject(), is("새로운 이슈주제"));
	}

	@Test
	public void delete_no_login() throws Exception {
		long id = 1;
		template.delete(String.format("/issues/%d/delete", id), String.class);

		assertNotNull(issueRepository.findOne(Long.valueOf(id)));
	}

	@Test
	public void delete() throws Exception {
		long id = 2;
		basicAuthTemplate().delete(String.format("/issues/%d/delete", id), String.class);

		assertNull(issueRepository.findOne(Long.valueOf(id)));
	}
}
