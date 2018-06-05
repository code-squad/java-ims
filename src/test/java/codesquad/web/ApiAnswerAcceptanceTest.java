package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import codesquad.domain.IssueRepository;
import support.test.AcceptanceTest;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {

	@Resource
	private IssueRepository issueRepository;
	
    @Test
    public void create() throws Exception {
        String location = createResource("/api/issues/1/answers", "새댓글", basicAuthTemplate());
        
        ResponseEntity<String> dbAnswer = getResource(location, findDefaultUser());
        assertThat(dbAnswer.getBody().contains("새댓글"), is(true));
    }
    
  
}
