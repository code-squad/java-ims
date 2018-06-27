package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Comment;
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

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
    private CommentDto updateCommentInfo;

    private User writer;
    private User other;

    private Issue issue;

    @Before
    public void setUp() throws Exception {
        comment = new CommentDto("test comment");
        updateCommentInfo = new CommentDto("modified comment");

        writer = new User("colin", "1234", "colin");
        other = new User("jinbro", "1234", "jinbro");

        issue = new Issue("test subject", "test comment");
    }

    @Test
    public void create() {
        when(commentRepo.save(comment._toComment())).thenReturn(comment._toComment());
        commentService.create(writer, issue, comment);
        verify(commentRepo, times(1)).save(any());
    }

    @Test
    public void update() {
        when(commentRepo.findById(anyLong())).thenReturn(Optional.of(comment._toComment().writeBy(writer).toIssue(issue)));
        Comment modifiedComment = commentService.update(writer, issue, anyLong(), updateCommentInfo);
        assertEquals(updateCommentInfo.getComment(), modifiedComment.getComment());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_fail_UnAuthorization() {
        when(commentRepo.findById(anyLong())).thenReturn(Optional.of(comment._toComment().writeBy(writer).toIssue(issue)));
        commentService.update(other, issue, anyLong(), updateCommentInfo);
    }

    @Test(expected = EntityNotFoundException.class)
    public void update_fail_invalidId() {
        when(commentRepo.findById(anyLong())).thenReturn(Optional.empty());
        commentService.update(writer, issue, anyLong(), updateCommentInfo);
    }
}