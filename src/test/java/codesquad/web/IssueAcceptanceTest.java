package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Before;
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
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private UserRepository userRepository;
	// 이슈등록페이지 로드하는데는 유저 구분 필요없음.
	
	private User otherUser;
	@Before
	public void create_other_user() {
		otherUser = new User(3, "other", "password", "otheruser");
		userRepository.save(otherUser);
	}
	@Test
	public void createForm_no_login() throws Exception {// 이슈등록 페이지가 잘 로드 되는지 테스트.
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}
	
	@Test
	public void create_issueForm_login() throws Exception {// 이슈등록 페이지가 잘 로드 되는지 테스트.
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		// http 응답 메세지의 location 확인하는 테스트 코드.
//		assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void create_issue_no_login() throws Exception {
		String subject = "testSubject";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", "comment, comment").build();
		
        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertEquals(Optional.empty(), issueRepository.findBySubject(subject));
        // 이슈추가 후 이슈목록페이지로 이동.
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void create_issue_login() throws Exception {// 이슈가 제대로 생성되는지 test
		String subject = "testSubject";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", "comment, comment").build();
		
        ResponseEntity<String> response = basicAuthTemplate.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(issueRepository.findBySubject(subject));
        // 이슈추가 후 이슈목록페이지로 이동.
//		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));

		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void update_issueForm_no_login() throws Exception {
		Issue issue = new Issue(1, loginUser, "test", "testcomment");
		issueRepository.save(issue);
		ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d/form", issue.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		// 로그인 페이지.
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void update_issueForm_login_other_user() throws Exception {
		Issue issue = new Issue(1, otherUser, "test", "testcomment");
		issueRepository.save(issue);
		ResponseEntity<String> response = basicAuthTemplate.getForEntity(String.format("/issues/%d/form", issue.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		// 로그인 페이지.
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void update_issueForm_login_right_user() throws Exception {
		Issue issue = new Issue(1, loginUser, "test", "testcomment");
		issueRepository.save(issue);
		ResponseEntity<String> response = basicAuthTemplate.getForEntity(String.format("/issues/%d/form", issue.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void update_issue_fail_no_login() throws Exception {
		Issue issue = new Issue(1, loginUser, "test", "testcomment");
		issueRepository.save(issue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
                .addParameter("subject", "updatedSubject")
                .addParameter("comment", "updatedComment").build();
		ResponseEntity<String> response = template.postForEntity(String.format("/issues/%d", issue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
		// loginForm
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void update_issue_fail_diffrent_user() throws Exception {
		Issue issue = new Issue(1, otherUser, "test", "testcomment");
		issueRepository.save(issue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
			    .addParameter("subject", "updatedSubject")
                .addParameter("comment", "updatedComment").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d", issue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/users/loginForm"));
		// loginForm
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void update_issue_success_right_user() throws Exception {
		Issue issue = new Issue(1, loginUser, "test", "testcomment");
		issueRepository.save(issue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
				.addParameter("subject", "updatedSubject")
                .addParameter("comment", "updatedComment").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d", issue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains("updatedSubject"));

		// show detail page
		log.debug("body : {}", response.getBody());
	}

	@Test
	public void remove_no_login() throws Exception {
		Issue issue = new Issue(1, loginUser, "test", "testcomment");
		issueRepository.save(issue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete").build();
		ResponseEntity<String> response = template.postForEntity(String.format("/issues/%d", issue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
		// loginForm
	}
	
	@Test
	public void remove_login_other_user() throws Exception {
		Issue issue = new Issue(1, otherUser, "test", "testcomment");
		issueRepository.save(issue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d", issue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/users/loginForm"));
		// loginForm
	}
	
	@Test
	public void remove_login_right_user() throws Exception {
		Issue issue = new Issue(1, loginUser, "test", "testcomment");
		issueRepository.save(issue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d", issue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		// show list page
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
	}
	
	@Test
	public void create_check_ifSubjectIsNotTyped() throws Exception {
		// 제목이나 내용 입력안했으면 다음페이지로 넘어가지 않음.
		String blank = "";
		String subject = Issue.getOptionalBlank(blank);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", "comment, comment").build();
		
        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        // 이슈추가 후 이슈목록페이지로 이동.
		assertThat(response.getHeaders().getLocation().getPath(), is("/issues"));
	}
	

	@Test
	public void create_check_ifCommentIsNotTyped() throws Exception {
		// 제목이나 내용 입력안했으면 다음페이지로 넘어가지 않음.
		String blank = "";
		String comment = Issue.getOptionalBlank(blank);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", "subject")
                .addParameter("comment", comment).build();
		
        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        // 이슈추가 후 이슈목록페이지로 이동.
		assertThat(response.getHeaders().getLocation().getPath(), is("/issues"));
	}
	
	@Test
	public void show_issues_list() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void show_issue_detail() throws Exception {	
		Issue issue = new Issue(1, loginUser, "test", "testcomment");
		issueRepository.save(issue);
		ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d",  issue.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	
}
