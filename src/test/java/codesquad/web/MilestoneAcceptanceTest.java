package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.MilestoneRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class MilestoneAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(MilestoneAcceptanceTest.class);

	@Autowired
	private MilestoneRepository milestoneRepository;

	@Test
	public void test_view_milestoneForm() {
		ResponseEntity<String> response = template.getForEntity("/milestones", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		String body = response.getBody();
		log.debug("body : {}", body);
		assertTrue(body.contains("Milestones"));
	}

	@Test
	public void test_view_milestoneList() {
		ResponseEntity<String> response = template.getForEntity("/milestones/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		String body = response.getBody();
		log.debug("body : {}", body);
		assertTrue(body.contains("check_circle"));
	}

	@Test
	public void test_save_milestone() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
		.addParameter("title", "테스트")
		.addParameter("startDate", "2018-01-18T18:32")
		.addParameter("endDate", "2018-01-25T18:32")
		.build();
		log.debug("date :  {}", LocalDateTime.now().toString());
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/milestones", request, String.class);
		assertThat(milestoneRepository.findAll().size(), is(3));
//		assertNotNull(milestoneRepository.findOne((long) 1));
	}
}
