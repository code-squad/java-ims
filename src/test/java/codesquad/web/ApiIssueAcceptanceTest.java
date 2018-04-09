package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.dto.IssueDto;
import support.test.AcceptanceTest;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
	@Resource
	private IssueRepository issueRepository;

	@Resource
	private UserRepository userRepository;

	@Test
	public void create_login() {
		IssueDto issue = new IssueDto("test1", "test1 comment");
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/issue/newIssue", issue, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
	}

	@Test
	public void create_no_login() {
		IssueDto issue = new IssueDto("test2", "test2 comment");
		ResponseEntity<String> response = template().postForEntity("/api/issue/newIssue", issue, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}

	@Test
	public void show() {
		//make issue
		IssueDto issue = new IssueDto("test3", "test3 comment");
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/api/issue/newIssue", issue, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		//show test
		Issue madeIssue = issueRepository.findBySubject("test3");
		ResponseEntity<String> response2 = basicAuthTemplate().getForEntity(String.format("/api/issue/%d", madeIssue.getId()), String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void update_login() {
		User user = userRepository.findOne((long) 1);
		//make issue
		IssueDto issue = new IssueDto("test4", "test4 comment");
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/api/issue/newIssue", issue, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		Issue dbIssue = issueRepository.findBySubject("test4");
		
		//update test
		IssueDto updateIssueDto = new IssueDto("test4", "update complete.");
		basicAuthTemplate(user).put(String.format("/api/issue/%d/", dbIssue.getId()), updateIssueDto, String.class);
		
		assertNotNull(issueRepository.findBySubject("test4"));
		Issue updateIssue = issueRepository.findBySubject("test4");
		assertEquals(updateIssue.getComment(), "update complete.");
	}
	
	@Test
	public void update_no_login() {
		User user = userRepository.findOne((long) 1);
		//make issue
		IssueDto issue = new IssueDto("test5", "test5 comment");
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/api/issue/newIssue", issue, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		Issue dbIssue = issueRepository.findBySubject("test5");
		
		//update test
		IssueDto updateIssueDto = new IssueDto("test5", "update complete.");
		template().put(String.format("/api/issue/%d/", dbIssue.getId()), updateIssueDto, String.class);
		
		assertNotNull(issueRepository.findBySubject("test5"));
		Issue updateIssue = issueRepository.findBySubject("test5");
		assertNotEquals(updateIssue.getComment(), "update complete.");
		assertEquals(updateIssue.getComment(), "test5 comment");
	}
	
	@Test
	public void update_anotherUser_login() {
		User user = userRepository.findOne((long) 2);
		//make issue
		IssueDto issue = new IssueDto("test6", "test6 comment");
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/api/issue/newIssue", issue, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		Issue dbIssue = issueRepository.findBySubject("test6");
		
		//update test
		IssueDto updateIssueDto = new IssueDto("test6", "update complete.");
		basicAuthTemplate().put(String.format("/api/issue/%d/", dbIssue.getId()), updateIssueDto, String.class);
		
		assertNotNull(issueRepository.findBySubject("test6"));
		Issue updateIssue = issueRepository.findBySubject("test6");
		assertNotEquals(updateIssue.getComment(), "update complete.");
		assertEquals(updateIssue.getComment(), "test6 comment");
	}
	
	@Test
	public void delete_login() {
		User user = userRepository.findOne((long) 1);
		//make issue
		IssueDto issue = new IssueDto("test7", "test7 comment");
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/api/issue/newIssue", issue, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		Issue dbIssue = issueRepository.findBySubject("test7");
		
		//delete test
		assertFalse(issueRepository.findBySubject("test7").isDeleted());
		basicAuthTemplate(user).delete(String.format("/api/issue/%d", dbIssue.getId()));
		assertTrue(issueRepository.findBySubject("test7").isDeleted());
	}
	
	@Test
	public void delete_no_login() {
		User user = userRepository.findOne((long) 1);
		//make issue
		IssueDto issue = new IssueDto("test8", "test8 comment");
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/api/issue/newIssue", issue, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		Issue dbIssue = issueRepository.findBySubject("test8");
		
		//delete test
		assertFalse(issueRepository.findBySubject("test8").isDeleted());
		template().delete(String.format("/api/issue/%d", dbIssue.getId()));
		assertFalse(issueRepository.findBySubject("test8").isDeleted());
	}
	
	@Test
	public void delete_anotherUser_login() {
		User user = userRepository.findOne((long) 2);
		//make issue
		IssueDto issue = new IssueDto("test9", "test9 comment");
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/api/issue/newIssue", issue, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		Issue dbIssue = issueRepository.findBySubject("test9");
		
		//delete test
		assertFalse(issueRepository.findBySubject("test9").isDeleted());
		basicAuthTemplate().delete(String.format("/api/issue/%d", dbIssue.getId()));
		assertFalse(issueRepository.findBySubject("test9").isDeleted());
	}
}
