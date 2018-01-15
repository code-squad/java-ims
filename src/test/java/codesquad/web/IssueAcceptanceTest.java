package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger Log = LoggerFactory.getLogger(UserAcceptanceTest.class);

	@Autowired
	private IssueRepository issueRepository;

	@Test
	public void createIssueFormPageWithoutLogin() {
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		String body = response.getBody();
		Log.debug("body : {}", body);
		assertTrue(body.contains("Login Member"));
	}
	
	@Test
	public void createIssueFormPageLoginStatus() {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		String body = response.getBody();
		Log.debug("body : {}", response.getBody());
		assertTrue(body.contains("Subject"));
	}

	@Test
	public void createIssueWithoutLogin() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("title", "제목").addParameter("contents", "내용").build();
		ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
		String body = response.getBody();
		assertTrue(body.contains("Login Member"));
	}
	
	@Test
	public void createIssueLoginStatus() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("title", "제목").addParameter("contents", "내용").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues", request, String.class);
		int numberOfIssue = issueRepository.findAll().size();
		assertThat(numberOfIssue, is(3)); // import.sql에 2개 더 있음.
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND)); // 리다이렉트 - 302
	}

	@Test
	public void showDetail() {
		ResponseEntity<String> response = template.getForEntity("/issues/1", String.class);
		Log.debug("body : {} " + response.getBody());
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(issueRepository.findOne((long) 1).getTitle(), is("11111"));
	}
	
	
	@Test
	public void showUpdateViewError() {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/2/updateForm", String.class);
		String body = response.getBody();
		Log.debug("body : {} " + body);
		assertTrue(body.contains("자신이 쓴 글만 수정"));
	}
	
	@Test
	public void issueUpdateTest() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
		.put()		
		.addParameter("title", "33333")
		.addParameter("contents", "33333").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues/1", request, String.class);
		showUpdateView();
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		Issue issue = issueRepository.findOne((long) 1);
		assertThat(issue.getTitle(), is("33333"));
	}
	
	private void showUpdateView() {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/1/updateForm", String.class);
		String body = response.getBody();
		Log.debug("body : {} " + body);
		assertTrue(body.contains("33333"));
	}
	
	@Test
	public void issueDeleteTest() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.delete().build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues/3", request, String.class);
		assertNull(issueRepository.findOne((long) 3));
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
	}
}
