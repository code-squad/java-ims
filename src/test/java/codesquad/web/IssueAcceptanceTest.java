package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
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
import codesquad.dto.IssueDto;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);
	@Autowired
	private IssueRepository issueRepository;
	
//	@Before
//	public void setUp() {
//		user = new User(1, "jiwon", "1111", "jiwonee");
//	}
	
	@Test
	public void createForm() throws Exception {// 이슈등록 페이지가 잘 로드 되는지 테스트.
		ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	@Test
	public void create() throws Exception {// 이슈가 제대로 생성되는지 test
		String subject = "testSubject";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("comment", "comment, comment").build();
		
        ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(issueRepository.findBySubject(subject));
        // 이슈추가 후 이슈목록페이지로 이동.
		assertThat(response.getHeaders().getLocation().getPath(), is("/"));
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
		ResponseEntity<String> response = template.getForEntity(String.format("/issues/%d",  1), String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	
}
