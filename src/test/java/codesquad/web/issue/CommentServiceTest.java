package codesquad.web.issue;

import codesquad.domain.history.DeleteHistoryRepository;
import codesquad.domain.issue.Comment;
import codesquad.domain.issue.CommentRepository;
import codesquad.service.IssueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import support.test.BaseTest;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;
import static support.test.Fixture.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest extends BaseTest {
    private static final Logger log = getLogger(CommentServiceTest.class);

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IssueService issueService;

    @Mock
    private DeleteHistoryRepository deleteHistoryRepository;

    @InjectMocks
    private CommentService commentService;

    @Before
    public void setUp() throws Exception {
        when(issueService.findById(ISSUE.getId())).thenReturn(ISSUE);
    }

    @Test
    public void create() {
        Comment savedComment = commentService.create(BRAD, ISSUE.getId(), COMMENT);
        softly.assertThat(savedComment.getWriter()).isEqualTo(BRAD);
        softly.assertThat(savedComment.getIssue()).isEqualTo(ISSUE);
        softly.assertThat(savedComment.getContents()).isEqualTo(CONTENTS);
    }

    @Test
    public void delete() {
        Comment newComment = new Comment(++RANDOM_COMMENT_ID, NEW_CONTENTS, ISSUE, BRAD);
        when(commentRepository.findById(newComment.getId())).thenReturn(Optional.of(newComment));
        commentService.delete(BRAD, ISSUE.getId(), newComment.getId());
        softly.assertThat(newComment.isDeleted()).isTrue();
    }

    @Test
    public void update() {
        Comment beforeComment = new Comment(++RANDOM_COMMENT_ID, NEW_CONTENTS, ISSUE, BRAD);
        Comment updateComment = new Comment(UPDATE_CONTENTS);
        when(commentRepository.findById(beforeComment.getId())).thenReturn(Optional.of(beforeComment));
        Comment updatedcomment = commentService.update(BRAD, ISSUE.getId(), beforeComment.getId(), updateComment);
        softly.assertThat(updatedcomment.getContents()).isEqualTo(UPDATE_CONTENTS);
    }
}