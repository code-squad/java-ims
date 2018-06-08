package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Attachment;
import codesquad.domain.AttachmentRepository;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class ApiAttachmentAcceptanceTest extends AcceptanceTest {

	@Resource
	private AttachmentRepository attachmentRepository;
	
	@Test
	public void download() throws Exception {
		
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.multipartFormData()
				.addParameter("file", new ClassPathResource("logback.xml")).build();
		basicAuthTemplate().postForEntity("/api/attachments/1", request, String.class);
		
		ResponseEntity<String> response = template.getForEntity("/api/attachments/1", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void upload() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.multipartFormData()
				.addParameter("file", new ClassPathResource("logback.xml")).build();
		ResponseEntity<Attachment> response = basicAuthTemplate().postForEntity("/api/attachments/1", request, Attachment.class);
		assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
	}
	

}
