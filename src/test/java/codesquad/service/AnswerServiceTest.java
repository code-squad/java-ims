package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.Answer;
import codesquad.domain.AnswerRepository;
import codesquad.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AnswerServiceTest {

    @Mock
    AnswerRepository answerRepository;

    @InjectMocks
    AnswerService answerService;

    private User loginUser = new User("javajigi", "test", "name");
    private User other = new User("sanjigi", "test", "name");

    @Test
    public void update_success() throws UnAuthenticationException {
        Answer origin = new Answer(loginUser, "this is content");
        origin.writtenBy(loginUser);
        when(answerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(origin));

        Answer updated = answerService.update(loginUser, anyLong(), "change content");
        assertThat(updated.getContent(), is("change content"));
    }

    @Test (expected = UnAuthenticationException.class)
    public void update_fail_other() throws UnAuthenticationException {
        Answer origin = new Answer(loginUser, "this is content");
        origin.writtenBy(loginUser);
        when(answerRepository.findById(anyLong())).thenReturn(Optional.ofNullable(origin));

        answerService.update(other, anyLong(), "change content");
    }

}
