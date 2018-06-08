package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.http.HttpStatus;

import codesquad.domain.AnswerRepository;
import codesquad.domain.IssueRepository;
import support.test.AcceptanceTest;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {

	@Resource
	private IssueRepository issueRepository;
	
	@Resource
	private AnswerRepository answerRepository;
	
	
    @Test
    public void create() throws Exception {
        String location = createResource(basicAuthTemplate(),"/api/issues/1/answers", "새댓글");
        assertThat(getResource(location, findDefaultUser()).getBody().contains("새댓글"), is(true));
        assertThat(answerRepository.findById(2L).isPresent(), is(true));
    }
    
    @Test
    public void update() {
    	basicAuthTemplate().put("/api/issues/1/answers/2", "새새댓글");
    	assertThat(getResource("/issues/1", findDefaultUser()).getBody().contains("새새댓글"), is(true));
        assertThat(getResource("/issues/1", findDefaultUser()).getStatusCode(), is(HttpStatus.OK));
    	assertThat(answerRepository.findById(2L).get().getComment(), is("새새댓글"));

    }
    

    @Test
    public void delete() {
    	assertThat(answerRepository.findById(1L).isPresent(), is(true));
    	basicAuthTemplate().delete("/api/issues/1/answers/1");
    	assertThat(answerRepository.findById(1L).isPresent(), is(false));
    }
  
}
