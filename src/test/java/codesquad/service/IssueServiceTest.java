package codesquad.service;


import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import support.test.AcceptanceTest;


@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest extends AcceptanceTest {

	@Mock
	private IssueRepository issueRepository;
	
	@Mock
	private AnswerRepository answerRepository;
	
	@InjectMocks
	private IssueService issueService;
	
	
	@Test
	public void createIssue() {
		IssueDto issueDto = new IssueDto("제목", "내용");
    	User user = new User(1L, "gram", "1234", "그램");
		issueService.add(user, issueDto);
		verify(issueRepository).save(issueDto.toIssue());
	}
	
	
    @Test
    public void createAnswer() throws Exception {
    	Issue issue = new Issue("제목","내용");
    	when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
    	User user = new User(1L, "gram", "1234", "그램");
    	issueService.addAnswer(user, 1L, "새댓글");
    	verify(answerRepository).save(new Answer(user, "새댓글", issue));
    }
    
    @Test
    public void deleteAnswer() {
    	User user = new User(1L, "gram", "1234", "그램");
    	Answer answer = new Answer(user, "내용", new Issue("제목", "내용"));
    	when(answerRepository.findById(1L)).thenReturn(Optional.of(answer));
    	issueService.deleteAnswer(user, 1L);
    	verify(answerRepository).delete(answer);
    }
    
  
}
