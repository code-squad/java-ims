package codesquad.web;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.annotation.Resource;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import codesquad.domain.AnswerRepository;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import support.test.AcceptanceTest;

public class ApiAnswerAcceptanceTest extends AcceptanceTest {

	@Resource
	private IssueRepository issueRepository;
	
	@Resource
	private AnswerRepository answerRepository;
	
	
    @Test
    public void create() throws Exception {
        String location = createResource("/api/issues/1/answers", "새댓글", basicAuthTemplate());
        ResponseEntity<String> dbAnswer = getResource(location, findDefaultUser());
        assertThat(dbAnswer.getBody().contains("새댓글"), is(true));
    }
    
    @Test
    public void update() {
    	basicAuthTemplate().put("/api/issues/1/answers/2", "새새댓글");
    	assertThat(answerRepository.findById(2L).get().getComment(), is("새새댓글"));
    }

    @Test
    public void delete() {
    	assertThat(answerRepository.findById(1L).isPresent(), is(true));
    	basicAuthTemplate().delete("/api/issues/1/answers/1");
    	assertThat(answerRepository.findById(1L).isPresent(), is(false));
    }
  
}
