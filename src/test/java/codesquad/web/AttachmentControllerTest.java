package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import javax.annotation.Resource;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.PathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.File;
import codesquad.domain.FileRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.service.FileService;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class AttachmentControllerTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(AttachmentControllerTest.class);

    @Autowired
	private IssueRepository issueRepository;
    
    @Autowired
	private FileRepository fileRepository;
    
    @Resource(name ="fileService")
	private FileService fileService;
	

	@Test
	public void download() throws Exception {// read
		// create issue test data
		String subject = "testSubject";
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", subject).addParameter("comment", "comment, comment").build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(issueRepository.findBySubject(subject));
		Issue issue = issueRepository.findBySubject(subject).get();
		
		// create uploaded file
		HttpEntity<MultiValueMap<String, Object>> request2 = HtmlFormDataBuilder.multipartFormData()
				.addParameter("file", new ClassPathResource("logback.xml")).build();
		ResponseEntity<String> result = basicAuthTemplate()
				.postForEntity(String.format("/issues/%d/attachments", issue.getId()), request2, String.class);
		assertEquals(HttpStatus.FOUND, result.getStatusCode());
		assertEquals("logback.xml", fileRepository.findByOriginalFileName("logback.xml").get().getOriginalFileName());

		// download test
		File file = fileRepository.findByOriginalFileName("logback.xml").get();
		log.debug("file: {}", file.toString());
		ResponseEntity<String> result3 = basicAuthTemplate()
				.getForEntity(String.format("/issues/%d/attachments/%d", issue.getId(), file.getId()), String.class);
		assertEquals(HttpStatus.OK, result3.getStatusCode());
		log.debug("body : {}", result3.getBody());
	}

    @Test
    public void upload() throws Exception {// write
    		// create issue test data
    		String subject = "testSubject";
		HttpEntity<MultiValueMap<String, Object>> request1 = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", subject).addParameter("comment", "comment, comment").build();

		ResponseEntity<String> response = basicAuthTemplate().postForEntity("/issues", request1, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(issueRepository.findBySubject(subject));
		Issue issue = issueRepository.findBySubject(subject).get();
		
        HttpEntity<MultiValueMap<String, Object>> request2 = HtmlFormDataBuilder
          .multipartFormData()
          .addParameter("file", new ClassPathResource("logback.xml"))
          .build();
        ResponseEntity<String> result = basicAuthTemplate().postForEntity(String.format("/issues/%d/attachments", issue.getId()), request2, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
        assertTrue(fileRepository.findByOriginalFileName("logback.xml").get().getFileName().contains("logback.xml"));          
    }
}