package codesquad.web;

import codesquad.domain.IssueRepository;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@After
	public void setup() {
		issueRepository.deleteAll();
	}

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body: {}", response.getBody());
	}

	@Test
	public void create() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", ISSUE_SUBJECT)
				.addParameter("comment", ISSUE_COMMENT).build();

		ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(issueRepository.findOne(Long.valueOf(1)));
		assertThat(response.getHeaders().getLocation().getPath(), is("/issues"));
	}

	@Test
	public void create_subject_null() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("comment", "이슈코멘트").build();

		ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_REQUIRED));
	}

	@Test
	public void create_comment_null() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "이슈주제").build();

		ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_REQUIRED));
	}

	@Test
	public void list() throws Exception {
		create();

		ResponseEntity<String> response = template.getForEntity("/issues", String.class);
		log.debug("body: {}", response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(ISSUE_COMMENT));
		assertTrue(response.getBody().contains(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))));
	}

	@Test
	public void show() throws Exception {
		create();

		ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);
		log.debug("body: {}", response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(ISSUE_COMMENT));
		assertTrue(response.getBody().contains(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))));
	}
}
