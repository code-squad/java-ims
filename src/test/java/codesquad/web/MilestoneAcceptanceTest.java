package codesquad.web;

import codesquad.domain.MilestoneRepository;
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

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);

	@Autowired
	private MilestoneRepository milestoneRepository;

	private static final String MILESTONE_SUBJECT = "마일스톤주제";
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	private static final String MILESTONE_START_DATE = LocalDateTime.now().format(formatter);
	private static final String MILESTONE_END_DATE = LocalDateTime.now().plusDays(7).format(formatter);

	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate().getForEntity("/milestones/form", String.class);
		log.debug("body: {}", response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void createForm_not_login() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/milestones/form", String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	private ResponseEntity<String> create(TestRestTemplate template) {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", MILESTONE_SUBJECT)
				.addParameter("startDate", MILESTONE_START_DATE)
				.addParameter("endDate", MILESTONE_END_DATE).build();

		return template.postForEntity("/milestones", request, String.class);
	}

	@Test
	public void create() throws Exception {
		ResponseEntity<String> response = create(basicAuthTemplate());

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(milestoneRepository.findOne(Long.valueOf(2)));
		assertThat(response.getHeaders().getLocation().getPath(), is("/milestones"));
	}

	@Test
	public void create_not_login() throws Exception {
		ResponseEntity<String> response = create(template);

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void create_subject_null() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("startDate", MILESTONE_START_DATE)
				.addParameter("endDate", MILESTONE_END_DATE).build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/milestones", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_REQUIRED));
	}

	@Test
	public void create_start_date_null() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", MILESTONE_SUBJECT)
				.addParameter("endDate", MILESTONE_END_DATE).build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/milestones", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_REQUIRED));
	}

	@Test
	public void create_end_date_null() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", MILESTONE_SUBJECT)
				.addParameter("startDate", MILESTONE_START_DATE).build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/milestones", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.PRECONDITION_REQUIRED));
	}

	@Test
	public void list() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/milestones", String.class);
		log.debug("body: {}", response.getBody());

		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains(MILESTONE_SUBJECT));
		assertTrue(response.getBody().contains(LocalDateTime.now().plusDays(7).format(DateTimeFormatter.ofPattern("MMM dd, yyyy"))));
	}
}
