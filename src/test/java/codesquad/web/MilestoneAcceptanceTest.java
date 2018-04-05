package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class MilestoneAcceptanceTest extends AcceptanceTest {
	@Resource(name="userRepository")
	private UserRepository userRepository;
	
	@Resource(name="milestoneRepository")
	private MilestoneRepository milestoneRepository;

	@Test
	public void list() {
		ResponseEntity<String> response = template().getForEntity("/milestone", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}
	
	@Test
	public void form() {
		ResponseEntity<String> response = template().getForEntity("/milestone/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}
	
	@Test
	public void create_login() {
		User user = userRepository.findOne((long) 1);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "testMilestone.")
				.addParameter("startDate", "2018-01-01-12:00:00")
				.addParameter("endDate", "2018-02-01-12:00:00").build();
		
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/milestone/newMilestone", request, String.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		Milestone dbMilestone = milestoneRepository.findBySubject("testMilestone.");
		assertNotNull(dbMilestone);
		assertEquals(dbMilestone.getStartDate(), "2018-01-01-12:00:00");
		assertEquals(dbMilestone.getEndDate(), "2018-02-01-12:00:00");
	}
	
	@Test
	public void create_no_login() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "testMilestone.")
				.addParameter("startDate", "2018-01-01-12:00:00")
				.addParameter("endDate", "2018-02-01-12:00:00").build();
		
		ResponseEntity<String> response = template().postForEntity("/milestone/newMilestone", request, String.class);
		
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
		assertNull(milestoneRepository.findBySubject("testMilestone."));
	}
	
	@Test
	public void show() {
		//make milestone
		User user = userRepository.findOne((long) 1);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "testMilestone.")
				.addParameter("startDate", "2018-01-01-12:00:00")
				.addParameter("endDate", "2018-02-01-12:00:00").build();
		basicAuthTemplate(user).postForEntity("/milestone/newMilestone", request, String.class);
		Milestone dbMilestone = milestoneRepository.findBySubject("testMilestone.");
		
		//show test
		ResponseEntity<String> showResponse = template().getForEntity(String.format("/milestone/%d", dbMilestone.getId()), String.class);
		assertThat(showResponse.getStatusCode(), is(HttpStatus.OK));
	}
}
