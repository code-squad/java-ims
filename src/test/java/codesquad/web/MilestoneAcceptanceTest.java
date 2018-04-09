package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class MilestoneAcceptanceTest extends AcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

	@Resource(name="userRepository")
	private UserRepository userRepository;

	@Resource(name="issueRepository")
	private IssueRepository issueRepository;

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
				.addParameter("subject", "testMilestone1")
				.addParameter("startDate", "2018-01-01-12:00:00")
				.addParameter("endDate", "2018-02-01-12:00:00").build();

		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/milestone/newMilestone", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Milestone dbMilestone = milestoneRepository.findBySubject("testMilestone1");
		assertNotNull(dbMilestone);
		assertEquals(dbMilestone.getStartDate(), "2018-01-01-12:00:00");
		assertEquals(dbMilestone.getEndDate(), "2018-02-01-12:00:00");
	}

	@Test
	public void create_no_login() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "testMilestone2")
				.addParameter("startDate", "2018-01-01-12:00:00")
				.addParameter("endDate", "2018-02-01-12:00:00").build();

		ResponseEntity<String> response = template().postForEntity("/milestone/newMilestone", request, String.class);

		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
		assertNull(milestoneRepository.findBySubject("testMilestone2"));
	}

	@Test
	@Transactional
	public void addIssue() {
		User user = userRepository.findOne((long) 1);
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "testIssue")
				.addParameter("comment", "this is test.").build();
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/issue/newIssue", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("testIssue");
		log.debug("issue is " + issue.toString());

		//make milestone
		HttpEntity<MultiValueMap<String, Object>> request2 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "testMilestone3")
				.addParameter("startDate", "2018-01-01-12:00:00")
				.addParameter("endDate", "2018-02-01-12:00:00").build();
		basicAuthTemplate(user).postForEntity("/milestone/newMilestone", request2, String.class);
		Milestone dbMilestone = milestoneRepository.findBySubject("testMilestone3");

		//addIssueTest
		ResponseEntity<String> totalResponse = basicAuthTemplate(user).getForEntity(String.format("/issue/%d/registerMilestone/%d", issue.getId(), dbMilestone.getId()), String.class);
		
		assertThat(totalResponse.getStatusCode(), is(HttpStatus.OK));
		assertEquals(milestoneRepository.findOne(dbMilestone.getId()).getIssues().size(), (int) 1);
	}
}
