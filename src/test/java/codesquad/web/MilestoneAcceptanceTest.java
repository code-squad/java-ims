package codesquad.web;

import codesquad.domain.MilestoneRepository;
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

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);
	
	@Autowired
	private MilestoneRepository milestoneRepository;

	@Test
	public void list() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "마일스톤Subject~")
				.addParameter("startDate", "2018-02-06 오후 03:06")
				.addParameter("endDate", "2018-02-10 오후 03:06").build();
		basicAuthTemplate.postForEntity("/milestones", request, String.class);

		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/milestones", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains("마일스톤Subject~"));
	}

	@Test
	public void create_form() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/milestones/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void create() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "마일스톤Subject~")
				.addParameter("startDate", "2018-02-06 오전 03:06")
				.addParameter("endDate", "2018-02-10 오후 03:06").build();

		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/milestones", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/milestones"));
		assertThat(milestoneRepository.findOne(1L).getSubject(), is("마일스톤Subject~"));
	}

}
