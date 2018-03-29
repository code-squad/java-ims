package codesquad.web;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.Label;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class LabelAcceptanceTest extends AcceptanceTest {
	private static final Logger log = LoggerFactory.getLogger(LabelAcceptanceTest.class);

	
	@Test
	public void create() throws Exception {
		ResponseEntity<String> response = createTestLabel(basicAuthTemplate(), "label title", "red");
		
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		log.info("body : {}", response.getBody());
	}
	
	@Test
	public void show() throws Exception{
		createTestLabel(basicAuthTemplate(), "label title", "red");
		
		ResponseEntity<String> response = basicAuthTemplate().getForEntity("/labels", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.info("body : {}", response.getBody());
		assertTrue(response.getBody().contains("label title"));
		assertTrue(response.getBody().contains("red"));
		log.info("body : {}", response.getBody());
	}
	
	@Test
	public void update() throws Exception{
		createTestLabel(basicAuthTemplate(), "update 전 label", "blue");
		
		HtmlFormDataBuilder dataBuilder = HtmlFormDataBuilder.urlEncodedForm().addParameter("title","new label")
				.addParameter("color", "cyan");
		HttpEntity<MultiValueMap<String, Object>> request = dataBuilder.build();
		basicAuthTemplate().put(String.format("/labels/%d", findLabelId("update 전 label")), request);
		
		ResponseEntity<String> response = basicAuthTemplate().getForEntity("/labels", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.info("body : {}", response.getBody());
		assertTrue(response.getBody().contains("new label"));
		assertTrue(!response.getBody().contains("update 전 label"));
	}
	
	@Test
	public void delete() throws Exception{
		createTestLabel(basicAuthTemplate(), "delete 전 label", "black");
		
		basicAuthTemplate().delete(String.format("/labels/%d", findLabelId("delete 전 label")));
		
		ResponseEntity<String> response = basicAuthTemplate().getForEntity("/labels", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.info("body : {}", response.getBody());

		assertTrue(!response.getBody().contains("delete 전 label"));
	}
	
	

}
