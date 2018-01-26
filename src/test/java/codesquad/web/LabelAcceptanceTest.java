package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNotNull;
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

import codesquad.domain.LabelRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class LabelAcceptanceTest extends BasicAuthAcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(LabelAcceptanceTest.class);

	@Autowired
	private LabelRepository labelRepository;

	@Test
	public void test_labelList() {
		ResponseEntity<String> response = template.getForEntity("/labels", String.class);
		String body = response.getBody();
		log.debug("body : {}", body);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertNotNull(body.contains("라벨"));
	}

	@Test
	public void test_labelView() {
		ResponseEntity<String> response = template.getForEntity("/labels/form", String.class);
		String body = response.getBody();
		log.debug("body : {}", body);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void test_addLabel() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("title", "라벨4").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/labels", request, String.class);
		String body = response.getBody();
		log.debug("body : {}", body);
		assertThat(labelRepository.findAll().size(), is(4));
		test_deleteMapping();
	}

	@Test
	public void test_updateLabelView() {
		ResponseEntity<String> response = template.getForEntity("/labels/1", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
	}

	@Test
	public void test_updateLabel() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm().put()
				.addParameter("title", "라벨1-1").build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/labels/1", request, String.class);
		String body = response.getBody();
		log.debug("body : {}", body);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(body.contains("라벨1-1"));
	}
	
	public void test_deleteMapping() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.delete().build();
		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/labels/3", request, String.class);
		String body = response.getBody();
		log.debug("body : {}", body);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		assertTrue(!body.contains("라벨3"));
	}
}
