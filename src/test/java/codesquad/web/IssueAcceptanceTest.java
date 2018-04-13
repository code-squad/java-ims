package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.MilestoneRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

	@Autowired
	private IssueRepository issueRepository;

	@Resource
	private UserRepository userRepository;

	@Resource
	private MilestoneRepository milestoneRepository;

	@Test
	public void form() {
		ResponseEntity<String> response = template.getForEntity("/issue/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void create_login() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test subject.")
				.addParameter("comment", "test comment.").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue/newIssue", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("test subject.");
		log.debug("issue is " + issue.toString());
		assertNotNull(issueRepository.findBySubject("test subject."));

		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
	}

	@Test
	public void create_no_login() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "no-login-create")
				.addParameter("comment", "no-login-create").build();
		ResponseEntity<String> response = template.postForEntity("/issue/newIssue", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
		assertNull(issueRepository.findBySubject("no-login-create"));
	}

	@Test
	public void show() {
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "show test.")
				.addParameter("comment", "this is show test.").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue/newIssue", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("show test.");
		log.debug("issue is " + issue.toString());

		//show issue with no login (expected status is OK)
		ResponseEntity<String> response2 = template.getForEntity(String.format("/issue/%d", issue.getId()), String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.OK));

		//show issue with login (expected status is OK)
		ResponseEntity<String> response3 = basicAuthTemplate.getForEntity(String.format("/issue/%d", issue.getId()), String.class);
		assertThat(response3.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void updateForm() {
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "update test")
				.addParameter("comment", "this is update test.").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue/newIssue", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("update test");
		log.debug("issue is " + issue.toString());

		//update form test
		ResponseEntity<String> response2 = basicAuthTemplate.getForEntity(String.format("/issue/%d/updateIssue", issue.getId()), String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void update_login() {
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "login update")
				.addParameter("comment", "this is update test.").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue/newIssue", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("login update");
		log.debug("issue is " + issue.toString());

		//update test
		HttpEntity<MultiValueMap<String, Object>> request2 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("comment", "this is updated!!").build();
		basicAuthTemplate.put(String.format("/issue/%d", issue.getId()), request2);
		Issue dbIssue = issueRepository.findBySubject("login update");
		assertEquals(dbIssue.getComment(), "this is updated!!");
	}

	@Test
	public void update_no_login() {
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "no login update")
				.addParameter("comment", "this is update test.").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue/newIssue", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("no login update");
		log.debug("issue is " + issue.toString());

		//update test (no login user)
		HttpEntity<MultiValueMap<String, Object>> request2 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("comment", "this is updated!!").build();
		template.put(String.format("/issue/%d", issue.getId()), request2);

		Issue dbIssue = issueRepository.findBySubject("no login update");
		assertNotEquals(dbIssue.getComment(), "this is updated!!");
		assertEquals(dbIssue.getComment(), "this is update test.");

		//update test (another user)
		User user = userRepository.findOne((long) 2);
		HttpEntity<MultiValueMap<String, Object>> request3 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("comment", "this is updated!!").build();
		basicAuthTemplate(user).put(String.format("/issue/%d", issue.getId()), request3);

		Issue dbIssue2 = issueRepository.findBySubject("no login update");
		assertNotEquals(dbIssue2.getComment(), "this is updated!!");
		assertEquals(dbIssue2.getComment(), "this is update test.");
	}

	@Test
	public void delete_login() {
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "delete-login-test")
				.addParameter("comment", "this is delete test.").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue/newIssue", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("delete-login-test");
		log.debug("issue is " + issue.toString());
		assertFalse(issue.isDeleted());

		//delete test
		basicAuthTemplate.delete(String.format("/issue/%d/deleteIssue", issue.getId()));
		Issue dbIssue = issueRepository.findBySubject("delete-login-test");
		assertTrue(dbIssue.isDeleted());
	}

	@Test
	public void delete_no_login() {
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "delete-login-test2")
				.addParameter("comment", "this is delete test.").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issue/newIssue", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("delete-login-test2");
		log.debug("issue is " + issue.toString());
		assertFalse(issue.isDeleted());

		//delete test (guest delete test)
		template.delete(String.format("/issue/%d/deleteIssue", issue.getId()));
		Issue dbIssue = issueRepository.findBySubject("delete-login-test2");
		assertFalse(dbIssue.isDeleted());

		//delete test (another user delete test)
		User user = userRepository.findOne((long) 2);
		basicAuthTemplate(user).delete(String.format("/issue/%d/deleteIssue", issue.getId()));
		Issue dbIssue2 = issueRepository.findBySubject("delete-login-test2");
		assertFalse(dbIssue2.isDeleted());
	}

	@Test
	public void registerMilestone() {
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "milestone issue")
				.addParameter("comment", "this is test.").build();
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issue/newIssue", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		long issueId = issueRepository.findBySubject("milestone issue").getId();

		//make milestone
		HttpEntity<MultiValueMap<String, Object>> request2 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "testMilestone1")
				.addParameter("startDate", "2018-01-01-12:00:00")
				.addParameter("endDate", "2018-02-01-12:00:00").build();

		ResponseEntity<String> milestoneResponse = basicAuthTemplate().postForEntity("/milestone/newMilestone", request2, String.class);
		assertThat(milestoneResponse.getStatusCode(), is(HttpStatus.FOUND));
		long milestoneId = milestoneRepository.findBySubject("testMilestone1").getId();

		//register milestone test
		ResponseEntity<String> totalResponse = basicAuthTemplate().getForEntity(String.format("/issue/%d/milestones/%d", issueId, milestoneId), String.class);
		assertThat(totalResponse.getStatusCode(), is(HttpStatus.OK));
		assertEquals(issueRepository.findBySubject("milestone issue").getMilestone().getId(), milestoneId);
	}

	@Test
	public void makeManager() {
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "testIssue")
				.addParameter("comment", "this is test.").build();
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issue/newIssue", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("testIssue");
		log.debug("issue is " + issue.toString());

		//makeManager test
		ResponseEntity<String> totalResponse = basicAuthTemplate().getForEntity(String.format("/issue/%d/setAssignee/%d", issue.getId(), (long) 2), String.class);

		assertThat(totalResponse.getStatusCode(), is(HttpStatus.OK));
		assertEquals(issueRepository.findBySubject("testIssue").getManager(), userRepository.findOne((long) 2));
	}
}
