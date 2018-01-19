package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.annotation.Resource;

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
import codesquad.domain.Label;
import codesquad.domain.LabelRepository;
import codesquad.domain.MileStone;
import codesquad.domain.MileStoneRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import codesquad.service.IssueService;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);
	@Autowired
	private IssueRepository issueRepository;
	@Autowired
	private MileStoneRepository mileStoneRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LabelRepository labelRepository;
	// 이슈등록페이지 로드하는데는 유저 구분 필요없음.

	@Resource(name = "issueService")
	private IssueService issueService;
	
	@Test
	public void createForm_no_login() throws Exception {// 이슈등록 페이지가 잘 로드 되는지 테스트.
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}
	
	@Test
	public void create_issueForm_login() throws Exception {// 이슈등록 페이지가 잘 로드 되는지 테스트.
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
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
//        assertEquals(null, issueRepository.findBySubject(subject).get().getSubject());
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
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void update_issueForm_no_login() throws Exception {
		User nologinUser = new User("chloe", "password", "chloe");
		nologinUser = userRepository.save(nologinUser);
		Issue nologinUserIssue = new Issue(2, nologinUser, "test2", "testcomment");
		nologinUserIssue = issueRepository.save(nologinUserIssue);
		ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d/form", nologinUser.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("" + nologinUserIssue.getId());
		// 로그인 페이지.
		log.debug("body : {}", response.getBody());
		issueRepository.delete(nologinUserIssue);
		userRepository.delete(nologinUser);
	}
	
	@Test
	public void update_issueForm_login_other_user() throws Exception {
		User otherUser = new User("other", "password", "otheruser");		
		otherUser = userRepository.save(otherUser);
		Issue otherUserIssue = new Issue(otherUser, "test2", "testcomment");
		otherUserIssue = issueRepository.save(otherUserIssue);
		ResponseEntity<String> response = basicAuthTemplate.getForEntity(String.format("/issues/%d/form", otherUserIssue.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		// 로그인 페이지.
		log.debug("body : {}", response.getBody());
		issueRepository.delete(otherUserIssue);
		userRepository.delete(otherUser);
	}
	
	@Test
	public void update_issueForm_login_right_user() throws Exception {
		Issue loginUserIssue = new Issue(loginUser, "test3", "testcomment");
		loginUserIssue = issueRepository.save(loginUserIssue);
		ResponseEntity<String> response = basicAuthTemplate.getForEntity(String.format("/issues/%d/form", loginUserIssue.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
		issueRepository.delete(loginUserIssue);
	}
	
	@Test
	public void update_issue_fail_no_login() throws Exception {
		Issue loginUserIssue = new Issue(4, loginUser, "test", "testcomment");
		loginUserIssue = issueRepository.save(loginUserIssue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
                .addParameter("subject", "updatedSubject")
                .addParameter("comment", "updatedComment").build();
		ResponseEntity<String> response = template.postForEntity(String.format("/issues/%d", loginUserIssue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
		// loginForm
		log.debug("body : {}", response.getBody());
		issueRepository.delete(loginUserIssue);
	}
		
	@Test
	public void update_issue_fail_diffrent_user() throws Exception {
		User otherUser = new User(3, "other", "password", "otheruser");		
		otherUser = userRepository.save(otherUser);
		Issue otherUserIssue = new Issue(5, otherUser, "test", "testcomment");
		otherUserIssue = issueRepository.save(otherUserIssue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
			    .addParameter("subject", "updatedSubject")
                .addParameter("comment", "updatedComment").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d", otherUserIssue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/users/loginForm"));
		// loginForm
		log.debug("body : {}", response.getBody());
		issueRepository.delete(otherUserIssue);
		userRepository.delete(otherUser);
	}
		
	@Test
	public void update_issue_success_right_user() throws Exception {
		Issue loginUserIssue = new Issue(6, loginUser, "test", "testcomment");
		loginUserIssue = issueRepository.save(loginUserIssue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "put")
				.addParameter("subject", "updatedSubject")
                .addParameter("comment", "updatedComment").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d", loginUserIssue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains("updatedSubject"));

		// show detail page
		log.debug("body : {}", response.getBody());
		issueRepository.delete(loginUserIssue);
	}
	
	@Test
	public void remove_no_login() throws Exception {
		Issue loginUserIssue = new Issue(7, loginUser, "test", "testcomment");
		loginUserIssue = issueRepository.save(loginUserIssue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete").build();
		ResponseEntity<String> response = template.postForEntity(String.format("/issues/%d", loginUserIssue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
		issueRepository.delete(loginUserIssue);
		// loginForm
	}
	
	@Test
	public void remove_login_other_user() throws Exception {
		User otherUser = new User(3, "other", "password", "otheruser");		
		otherUser = userRepository.save(otherUser);
		Issue otherUserIssue = new Issue(8, otherUser, "test", "testcomment");
		otherUserIssue = issueRepository.save(otherUserIssue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d", otherUserIssue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/users/loginForm"));
		// loginForm
		issueRepository.delete(otherUserIssue);
		userRepository.delete(otherUser);
	}
	
	@Test
	public void remove_login_right_user() throws Exception {
		Issue loginUserIssue = new Issue(9, loginUser, "test", "testcomment");
		loginUserIssue = issueRepository.save(loginUserIssue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("_method", "delete").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d", loginUserIssue.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		// show list page
		assertTrue(response.getHeaders().getLocation().getPath().startsWith("/"));
		issueRepository.delete(loginUserIssue);
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
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
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
		assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
	}
	
	@Test
	public void show_issues_list() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void show_issue_detail() throws Exception {
		Issue loginUserIssue = new Issue(10, loginUser, "test10", "testcomment");
		loginUserIssue = issueRepository.save(loginUserIssue);
		ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d",  loginUserIssue.getId()), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
		issueRepository.delete(loginUserIssue);
	}
	
	@Test
	public void set_mileStone() throws Exception {
		MileStone mileStone = new MileStone(1, "subject", "startDate", "endDate");
		Issue loginUserIssue = new Issue(11, loginUser, "test", "testcomment");
		mileStone = mileStoneRepository.save(mileStone);
		loginUserIssue = issueRepository.save(loginUserIssue);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("", "").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d/setMileStone/%d", loginUserIssue.getId(), mileStone.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		// show list page
		log.debug("body : {}", response.getBody());
		issueRepository.delete(loginUserIssue);
		mileStoneRepository.delete(mileStone);
	}
	
	@Test
	public void set_assignedUser() throws Exception {
		User assignedUser = new User((long)3, "chloe", "password", "jiwon");
		Issue loginUserIssue = new Issue(12, loginUser, "test", "testcomment");
		loginUserIssue = issueRepository.save(loginUserIssue);
		assignedUser = userRepository.save(assignedUser);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("", "").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d/setAssignedUser/%d", loginUserIssue.getId(), assignedUser.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		// show list page
		log.debug("body : {}", response.getBody());
		issueRepository.delete(loginUserIssue);
		userRepository.delete(assignedUser);		
	}
	// 라벨 쪽에서 삭제하기 때문에 상관없음.(확인 필요)
	@Test
	public void set_label() throws Exception {
		Label label = new Label(1, "label");
		Issue loginUserIssue = new Issue(13, loginUser, "test", "testcomment");
		loginUserIssue = issueRepository.save(loginUserIssue);
		label = labelRepository.save(label);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("", "").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity(String.format("/issues/%d/setLabel/%d", loginUserIssue.getId(), label.getId()), request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		// show list page
		log.debug("body : {}", response.getBody());
		// n:m d
//		loginUserIssue.getLabels().remove(label);
//		issueRepository.save(loginUserIssue);
//		labelRepository.delete(label);
//		issueRepository.delete(loginUserIssue);
	}

}
