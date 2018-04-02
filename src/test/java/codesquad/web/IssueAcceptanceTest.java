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

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(UserAcceptanceTest.class);

	@Resource(name = "issueRepository")
	private IssueRepository issueRepository;

	@Test
	public void createForm_logined() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void createForm_no_logined() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains("Login Member"), is(true));
	}

	@Test
	public void createIssue_logined() {
		String subject = "testtitle";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", subject)
				.addParameter("comment", "contetnts")
				.build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(issueRepository.findBySubject(subject));
		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
	}

	@Test
	public void createIssue_no_logined() {
		String subject = "testtitle2";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", subject)
				.addParameter("comment", "contetnts")
				.build();
		ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/login"));
	}
	
	@Test
	public void deleteIssue_로그인_안된경우() {
		Issue loginUserIssue = new Issue(4L, "test", "testcomment", loginUser);
		loginUserIssue = issueRepository.save(loginUserIssue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("_method", "delete")
				.build();
		ResponseEntity<String> response = template.postForEntity(String.format("/issues/%d", loginUserIssue.getId()),
				request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/login"));
		issueRepository.delete(loginUserIssue);
	}
	
	@Test
	public void deleteIssue_같은_유저() {
		Issue loginUserIssue = new Issue(5L, "test", "testcomment", loginUser);
		loginUserIssue = issueRepository.save(loginUserIssue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("_method", "delete")
				.build();
		ResponseEntity<String> response = basicAuthTemplate
				.postForEntity(String.format("/issues/%d", loginUserIssue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
		issueRepository.delete(loginUserIssue);
	}
	
	@Test
	public void update_같은_유저() {
		String subject = "수정된 제목";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("_method", "put")
				.addParameter("subject", subject)
				.addParameter("comment", "수정된 내용이 들어간다.")
				.build();
		ResponseEntity<String> response = basicAuthTemplate
				.postForEntity(String.format("/issues/%d", 1L), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
		assertThat(issueRepository.findOne(1L).getSubject(), is(subject));
	}
	
	@Test
	public void update_로그인_안된_경우() {
		Issue issue = issueRepository.findOne(1L);
		String original = issue.getSubject();
		String subject = "새로 수정된 제목";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("_method", "put")
				.addParameter("subject", subject)
				.addParameter("comment", "수정된 내용이 들어간다.")
				.build();
		ResponseEntity<String> response = template
				.postForEntity(String.format("/issues/%d", 1L), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
		assertThat(issueRepository.findOne(1L).getSubject(), is(original));
	}
	
	@Test
	public void update_다른_유저() {
		Long issueId = 2L;
		Issue issue = issueRepository.findOne(issueId);
		String original = issue.getSubject();
		String subject = "새로 수정된 제목";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("_method", "put")
				.addParameter("subject", subject)
				.addParameter("comment", "수정된 내용이 들어간다.")
				.build();
		ResponseEntity<String> response = template
				.postForEntity(String.format("/issues/%d", issueId), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
		assertThat(issueRepository.findOne(issueId).getSubject(), is(original));
	}
	
	@Test
	public void updateForm_로그인_유저() {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/1/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains("Modify"), is(true));
	}
	
	@Test
	public void updateForm_로그인_안된_유저() {
		ResponseEntity<String> response = template.getForEntity("/issues/1/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertThat(response.getBody().contains("Login Member"), is(true));
	}
}
