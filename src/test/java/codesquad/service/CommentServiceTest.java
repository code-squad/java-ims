package codesquad.service;

import codesquad.domain.CommentRepository;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.CommentDto;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepo;

    @InjectMocks
    private CommentService commentService;

    private CommentDto comment;
    private User writer;
    private Issue issue;

    @Before
    public void setUp() throws Exception {
        comment = new CommentDto("test comment");
        writer = new User("colin", "1234", "colin");
        issue = new Issue("test subject", "test comment");
    }

    @Test
    public void create() {
        when(commentRepo.save(comment._toComment())).thenReturn(comment._toComment());
        commentService.create(writer, issue, comment);
        verify(commentRepo, times(1)).save(any());
    }
}