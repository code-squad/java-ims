package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.service.IssueService;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Autowired
    IssueService issueService;
    
    
    public ResponseEntity<String> createTestIssue(TestRestTemplate myTemplate, String title, String contents) {
    	HtmlFormDataBuilder dataBuilder = HtmlFormDataBuilder.urlEncodedForm().addParameter("title", title).addParameter("contents", contents);
    	HttpEntity<MultiValueMap<String, Object>> request = dataBuilder.build();
    	return myTemplate.postForEntity("/issues", request, String.class);
    	
    }
    
	@Test
	public void createForm() throws Exception {
		ResponseEntity<String> response = template().getForEntity("/issues", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.info("body : {}", response.getBody());
	}
	
	@Test
	public void create() throws Exception {
		ResponseEntity<String> response = createTestIssue(basicAuthTemplate(), "create 제목", "create 내용");
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		ResponseEntity<String> response2 = template().getForEntity("/", String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response2.getBody().contains("create 제목"));
		assertTrue(response2.getBody().contains("ksm0814"));
		log.info("body : {}", response2.getBody());
	}
	
	@Test
	public void detail() throws Exception {
		createTestIssue(basicAuthTemplate(), "detail 제목", "detail 내용");
		
		Long issueId = Optional.ofNullable(findIssueId("detail 제목")).orElseThrow(() -> new MyExeption("해당하는 이슈를 찾지 못했습니다."));
		ResponseEntity<String> response = template().getForEntity(String.format("/issues/%d",issueId),  String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response.getBody().contains("detail 제목"));
		assertTrue(response.getBody().contains("detail 내용"));
		assertTrue(response.getBody().contains("ksm0814"));
		log.info("body : {}", response.getBody());
		
	}

	private Long findIssueId(String title) {
		for (Issue issue : issueService.findAll()) {
			if(issue.getTitle().equals(title))
				return issue.getId();
		}
		return null;
	}
	
	@Test
	public void update() throws Exception{
		createTestIssue(basicAuthTemplate(), "update전 제목", "update전 내용");
		
		HtmlFormDataBuilder dataBuilder = HtmlFormDataBuilder.urlEncodedForm().addParameter("title", "update후 제목").addParameter("contents", "update후 내용");
		HttpEntity<MultiValueMap<String, Object>> request = dataBuilder.build();
		basicAuthTemplate().put(String.format("/issues/%d", findIssueId("update전 제목")), request);
		
		ResponseEntity<String> response2 = template().getForEntity(String.format("/issues/%d", findIssueId("update후 제목")), String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.OK));
		log.info("body : {}", response2.getBody());
		assertTrue(response2.getBody().contains("update후 제목"));
		assertTrue(response2.getBody().contains("update후 내용"));
		assertTrue(response2.getBody().contains("ksm0814"));
	}
	
	@Test
	public void update_no_auth() throws Exception {
		createTestIssue(basicAuthTemplate(), "update fail전 제목", "update fail전 내용");
		
		HtmlFormDataBuilder dataBuilder = HtmlFormDataBuilder.urlEncodedForm().addParameter("title", "updatefail 후 제목").addParameter("contents", "updatefail후 내용");
		HttpEntity<MultiValueMap<String, Object>> request = dataBuilder.build();
		User noAuthUser = new User("sanjigi", "test", "산지기");
		basicAuthTemplate(noAuthUser).put(String.format("/issues/%d", findIssueId("update fail전 제목")), request);
		
		ResponseEntity<String> response2 = template().getForEntity(String.format("/issues/%d", findIssueId("updatefail 후 제목")), String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.BAD_REQUEST));
	}
	
	@Test
	public void delete() throws Exception{
		createTestIssue(basicAuthTemplate(), "delete 제목", "delete 내용");
		basicAuthTemplate().delete(String.format("/issues/%d", findIssueId("delete 제목")));
		
		ResponseEntity<String> response = template().getForEntity("/", String.class);
		log.info("body : {}", response.getBody());
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(!response.getBody().contains("delete 제목"));
	}
	

}
