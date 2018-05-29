package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;

import codesquad.domain.IssueRepository;
import support.test.BasicAuthAcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IssueAcceptanceTest extends BasicAuthAcceptanceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueAcceptanceTest.class);

    @Autowired
    private IssueRepository issueRepository;

    @Test
    public void createForm() throws Exception {
        ResponseEntity<String> response = template.getForEntity("/issues/form", String.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        log.debug("body : {}", response.getBody());
    }
    
    @Test
    public void create() {
         HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder.urlEncodedForm()
                 .addParameter("subject", "제목제목")
                 .addParameter("comment", "내용내용").build();
         ResponseEntity<String> response = template.postForEntity("/issues", request, String.class);
         assertThat(response.getStatusCode(), is(HttpStatus.FOUND));
         assertThat(issueRepository.findById(1L).isPresent(), is(true));
         assertThat(response.getHeaders().getLocation().getPath(), is("/"));
    }

    @Test
    public void showIssue() {
    	ResponseEntity<String>response = template.getForEntity("/issues/1", String.class);
    	assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }
    
    
}
