package codesquad.web;

import codesquad.domain.AttachmentRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

public class AttachmentAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(AttachmentAcceptanceTest.class);

	@Autowired
	private AttachmentRepository attachmentRepository;

	@Test
	public void download() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				.multipartFormData()
				.addParameter("file", new ClassPathResource("logback.xml"))
				.build();
		template.postForEntity("/attachments/issues/1", request, String.class);

		ResponseEntity<String> result = template.getForEntity("/attachments/1", String.class);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		log.debug("body : {}", result.getBody());
	}

	@Test
	public void upload() throws Exception {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
				.multipartFormData()
				.addParameter("file", new ClassPathResource("logback.xml"))
				.build();
		ResponseEntity<String> result = template.postForEntity("/attachments/issues/1", request, String.class);
		assertEquals(HttpStatus.FOUND, result.getStatusCode());
		assertNotNull(attachmentRepository.findOne(1L));
		assertThat(attachmentRepository.findOne(1L).getFileName(), is("logback.xml"));
	}
}
