package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class AttachmentControllerTest extends AcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);
	
	@Resource
	private IssueRepository issueRepository;

	@Test
	public void download() throws Exception {
		ResponseEntity<String> result = template.getForEntity("/attachments/1", String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		log.debug("body : {}", result.getBody());
	}

	@Test
	public void upload() throws Exception {
		//make issue
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test1")
				.addParameter("comment", "this is test.").build();
		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issue/newIssue", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));

		Issue issue = issueRepository.findBySubject("test1");
		log.debug("issue is " + issue.toString());
		assertFalse(issue.isDeleted());
		
		//upload file test
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				.multipartFormData()
				.addParameter("file", new ClassPathResource("logback.xml"))
				.build();
		ResponseEntity<String> result = basicAuthTemplate().postForEntity(String.format("/issue/%d/uploadFile", issue.getId()), request, String.class);
		assertEquals(HttpStatus.FOUND, result.getStatusCode());
	}
}