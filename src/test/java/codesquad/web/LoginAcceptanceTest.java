package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
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

import codesquad.service.UserService;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class LoginAcceptanceTest extends AcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(LoginAcceptanceTest.class);
    
    @Autowired
    UserService userService;
    
    @Test
    public void createForm() throws Exception{
    	ResponseEntity<String> response = template.getForEntity("/users/loginForm", String.class);
    	assertThat(response.getStatusCode(), is(HttpStatus.OK));
    	log.info("body : {}", response.getBody());
    }
    
    @Test
    public void login() throws Exception {
    	HtmlFormDataBuilder dataBuilder = HtmlFormDataBuilder.urlEncodedForm().addParameter("userId", "ksm0814").addParameter("password", "k5696");
		HttpEntity<MultiValueMap<String, Object>> request = dataBuilder.build();
		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		ResponseEntity<String> response2 = basicAuthTemplate().getForEntity("/", String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.OK));
		assertTrue(response2.getBody().contains("logout"));
		log.info("body : {}", response2.getBody());
    }
    
    @Test
    public void login_fail() throws Exception{
    	HtmlFormDataBuilder dataBuilder = HtmlFormDataBuilder.urlEncodedForm().addParameter("userId", "failId").addParameter("password", "failPw");
		HttpEntity<MultiValueMap<String, Object>> request = dataBuilder.build();
		ResponseEntity<String> response = template.postForEntity("/users/login", request, String.class);
		assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
		
		ResponseEntity<String> response2 = template().getForEntity("/", String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.OK));
		assertTrue(!response2.getBody().contains("logout"));
		log.info("body : {}", response2.getBody());
    }
    
    @Test
    public void logout() throws Exception {
		ResponseEntity<String> response2 = basicAuthTemplate().getForEntity("/users/logout", String.class);
		assertThat(response2.getStatusCode(), is(HttpStatus.OK));
    }
}
