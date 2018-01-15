package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.MileStoneRepository;
import support.domain.AbstractEntity;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class MileStoneAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(MileStoneAcceptanceTest.class);
	@Autowired
	private MileStoneRepository mileStoneRepository;
	
	@Test
	public void show_list_no_login() throws Exception {
		// 리스트 페이지에서 생성 버튼 눌러 생성.
		ResponseEntity<String> response = template.getForEntity("/mileStones", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));

		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void show_list_login() throws Exception {
		// 리스트 페이지에서 생성 버튼 눌러 생성.
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/mileStones", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void createForm_no_login() throws Exception {
		ResponseEntity<String> response = template.getForEntity("/mileStones/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void createForm_login() throws Exception {
		ResponseEntity<String> response = basicAuthTemplate.getForEntity("/mileStones/form", String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.OK));
		log.debug("body : {}", response.getBody());
	}
	
	@Test
	public void create_no_login() throws Exception {
		String subject = "mileStonesubject";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("startDate", "startDate")
                .addParameter("endDate", "endDate").build();

		ResponseEntity<String> response = template.postForEntity("/mileStones", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertEquals(Optional.empty(), mileStoneRepository.findBySubject(subject));
        assertNotNull(mileStoneRepository.findBySubject(subject));
        assertThat(response.getHeaders().getLocation().getPath(), is("/users/loginForm"));
	}
	
	@Test
	public void create_login() throws Exception {
		String subject = "mileStonesubject";
		HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("subject", subject)
                .addParameter("startDate", "startDate")
                .addParameter("endDate", "endDate").build();

		ResponseEntity<String> response = basicAuthTemplate.postForEntity("/mileStones", request, String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
        assertNotNull(mileStoneRepository.findBySubject(subject));
        // 생성 후 목록으로 redirect.
        assertTrue(response.getHeaders().getLocation().getPath().startsWith("/mileStones"));
	}
	
	@Test
	public void testName() throws Exception {
		
	}
	
}
