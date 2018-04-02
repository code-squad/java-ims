package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.MilestoneRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class MileStoneAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(MileStoneAcceptanceTest.class);

	@Resource(name = "milestoneRepository")
	private MilestoneRepository milestoneRepository;

	@Test
	public void list() {
		ResponseEntity<String> response = template.getForEntity("/milestones/list", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void form() {
		ResponseEntity<String> response = template.getForEntity("/milestones/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void createMilestone_로그인_유저() {
		String subject = "mysubject";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", subject)
				.addParameter("startDate", "2018-04-02T10:10")
				.addParameter("endDate", "2018-04-11T12:12").build();

		ResponseEntity<String> response = 
				basicAuthTemplate(findDefaultUser()).postForEntity("/milestones", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(milestoneRepository.findBySubject(subject).get().getSubject(), is(subject));
		assertThat(response.getHeaders().getLocation().getPath(), is("/milestones/list"));
	}
	
	@Test
	public void createMilestone_로그인_안된_유저() {
		String subject = "no logined";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", subject)
				.addParameter("startDate", "2018-04-02T10:10")
				.addParameter("endDate", "2018-04-11T12:12").build();

		ResponseEntity<String> response = 
				template.postForEntity("/milestones", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/login"));
	}
}
