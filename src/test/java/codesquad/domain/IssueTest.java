package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.domain.history.DeleteHistory;
import codesquad.domain.issue.Comment;
import codesquad.domain.issue.Issue;
import codesquad.domain.issue.IssueBody;
import org.junit.Test;
import org.slf4j.Logger;
import support.test.BaseTest;

import static codesquad.domain.CommentTest.*;
import static org.slf4j.LoggerFactory.getLogger;
import static support.test.Fixture.*;

public class IssueTest extends BaseTest {
    private static final Logger log = getLogger(IssueTest.class);

    public static Issue newIssue() {
        return new Issue(DEFAULT_ISSUE_ID, NEW_ISSUE_BODY, BRAD);
    }

    public static Issue newIssue(Long id) {
        return new Issue(id, NEW_ISSUE_BODY, BRAD);
    }

    public static Issue newIssue(String subject, String comment) {
        return new Issue(new IssueBody(subject, comment));
    }

    @Test
    public void wirttenBy() {
        ISSUE.writtenBy(BRAD);
        softly.assertThat(ISSUE.isOwner(BRAD)).isEqualTo(true);
    }

    @Test
    public void update() {
        Issue issue = newIssue();
        issue.update(BRAD, UPDATE_ISSUE_BODY);
        softly.assertThat(issue.hasSameBody(UPDATE_ISSUE_BODY));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_같은유저아닐떄() {
        ISSUE.update(JUNGHYUN, UPDATE_ISSUE_BODY);
    }

    @Test
    public void delete() {
        DeleteHistory deleteHistory = ISSUE.delete(BRAD);
        softly.assertThat(ISSUE.isDeleted()).isEqualTo(true);
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_다른유저() {
        ISSUE.delete(JUNGHYUN);
        softly.assertThat(ISSUE.isDeleted()).isEqualTo(false);
    }

    @Test
    public void addComment() {
        Comment newComment = new Comment(NEW_CONTENTS);
        ISSUE.addComment(newComment);
        softly.assertThat(ISSUE.getComments().contains(newComment)).isTrue();
        softly.assertThat(newComment.getIssue()).isEqualTo(ISSUE);
    }

    @Test
    public void deleteComment() {
        softly.assertThat(ISSUE.getComments().contains(COMMENT3)).isTrue();
        ISSUE.deleteComment(COMMENT3);
        softly.assertThat(ISSUE.getComments().contains(COMMENT)).isFalse();
    }

    @Test
    public void updateComment() {
        Issue newIssue = newIssue();
        Comment beforeComment = new Comment(RANDOM_COMMENT_ID, NEW_CONTENTS, ISSUE, BRAD);
        Comment updateComment = new Comment(RANDOM_COMMENT_ID, UPDATE_CONTENTS, ISSUE, BRAD);
        newIssue.getComments().add(beforeComment);
        newIssue.updateComment(updateComment);
        softly.assertThat(newIssue.getComments().get(0).getContents()).isEqualTo(UPDATE_CONTENTS);
    }
}