package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.LabelRepository;
import codesquad.domain.User;
import codesquad.domain.UserRepository;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class LabelAcceptanceTest extends AcceptanceTest{
	@Autowired
	private LabelRepository labelRepository;
	
	@Resource
	private UserRepository userRepository;

	@Test
	public void test() {
		ResponseEntity<String> response1 = basicAuthTemplate().getForEntity("/label", String.class);
		assertThat(response1.getStatusCode(), is(HttpStatus.OK));
		
		ResponseEntity<String> response2 = template().getForEntity("/label", String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.OK));
	}
	
	@Test
	public void form() {
		ResponseEntity<String> response1 = basicAuthTemplate().getForEntity("/label/form", String.class);
		assertThat(response1.getStatusCode(), is(HttpStatus.OK));
		
		ResponseEntity<String> response2 = template().getForEntity("/label/form", String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.FORBIDDEN));
	}
	
	@Test
	public void create_login() {
		User user = userRepository.findOne((long) 1);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test1").build();
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/label/newLabel", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		assertNotNull(labelRepository.findBySubject("test1"));
	}
	
	@Test
	public void create_no_login() {
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test2").build();
		ResponseEntity<String> response = template().postForEntity("/label/newLabel", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
		assertNull(labelRepository.findBySubject("test2"));
	}
	
	@Test
	public void updateform() {
		//make label
		User user = userRepository.findOne((long) 1);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test3").build();
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/label/newLabel", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		//update form test
		long id = labelRepository.findBySubject("test3").getId();
		ResponseEntity<String> updateResponse = basicAuthTemplate(user).getForEntity(String.format("/label/%d/updateLabel", id), String.class);
		assertThat(updateResponse.getStatusCode(), is(HttpStatus.OK));
	}
	
	@Test
	public void update_login() {
		//make label
		User user = userRepository.findOne((long) 1);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test4").build();
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/label/newLabel", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		//update test
		long id = labelRepository.findBySubject("test4").getId();
		HttpEntity<MultiValueMap<String, Object>> updateRequest = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test4_2").build();
		basicAuthTemplate(user).put(String.format("/label/%d", id), updateRequest);
		
		assertNotNull(labelRepository.findBySubject("test4_2"));
		assertNull(labelRepository.findBySubject("test4"));
		assertEquals(labelRepository.findBySubject("test4_2").getId(), id);
	}
	
	@Test
	public void update_no_login() {
		//make label
		User user = userRepository.findOne((long) 1);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test5").build();
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/label/newLabel", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		//update test
		long id = labelRepository.findBySubject("test5").getId();
		HttpEntity<MultiValueMap<String, Object>> updateRequest = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test5_2").build();
		template().put(String.format("/label/%d", id), updateRequest);
		
		assertNull(labelRepository.findBySubject("test5_2"));
		assertNotNull(labelRepository.findBySubject("test5"));
	}
	
	@Test
	public void delete_login() {
		//make label
		User user = userRepository.findOne((long) 1);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test6").build();
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/label/newLabel", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		//delete test
		long id = labelRepository.findBySubject("test6").getId();
		basicAuthTemplate().delete(String.format("/label/%d/deleteLabel", id));
		
		assertTrue(labelRepository.findBySubject("test6").isDeleted());
	}
	
	@Test
	public void delete_no_login() {
		//make label
		User user = userRepository.findOne((long) 1);
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
				.addParameter("subject", "test7").build();
		ResponseEntity<String> response = basicAuthTemplate(user).postForEntity("/label/newLabel", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		//delete test
		long id = labelRepository.findBySubject("test7").getId();
		template().delete(String.format("/label/%d/deleteLabel", id));
		
		assertFalse(labelRepository.findBySubject("test7").isDeleted());
	}
}
