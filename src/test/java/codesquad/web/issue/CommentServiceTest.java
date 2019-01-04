package codesquad.web.issue;

import codesquad.domain.issue.Comment;
import codesquad.domain.issue.CommentRepository;
import codesquad.service.IssueService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.test.BaseTest;

import static codesquad.domain.IssueTest.ISSUE;
import static codesquad.domain.UserTest.BRAD;
import static codesquad.domain.issue.CommentTest.COMMENT;
import static codesquad.domain.issue.CommentTest.CONTENTS;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CommentServiceTest extends BaseTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IssueService issueService;

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
}