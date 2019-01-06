package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.domain.issue.Comment;
import codesquad.domain.issue.Issue;
import codesquad.domain.issue.IssueBody;
import org.junit.Test;
import org.slf4j.Logger;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

import static codesquad.domain.UserTest.BRAD;
import static codesquad.domain.UserTest.JUNGHYUN;
import static codesquad.domain.issue.CommentTest.*;
import static org.slf4j.LoggerFactory.getLogger;

public class IssueTest extends BaseTest {
    private static final Logger log = getLogger(IssueTest.class);

    public static final long WRONG_ISSUE_ID = 100L;
    public static final long DEFAULT_ISSUE_ID = 0L;
    public static final List<Issue> issues = new ArrayList<>();
    public static final IssueBody ISSUE_BODY = new IssueBody("테스트 이슈1", "테스트 이슈 내용입니다1");
    public static final Issue ISSUE = new Issue(1L, ISSUE_BODY, BRAD);
    public static final IssueBody ISSUE_BODY2 = new IssueBody("테스트 이슈2", "테스트 이슈 내용입니다2");
    public static final Issue ISSUE2 = new Issue(2L, ISSUE_BODY2, BRAD);
    public static final IssueBody ISSUE_BODY3 = new IssueBody("테스트 이슈3", "테스트 이슈 내용입니다3");
    public static final Issue ISSUE3 = new Issue(3L, ISSUE_BODY3, JUNGHYUN);
    public static final IssueBody UPDATE_ISSUE_BODY = new IssueBody("업데이트 이슈 제목", "업데이트 이슈 내용입니다");
    public static final IssueBody NEW_ISSUE_BODY = new IssueBody("새로운 테스트 이슈 제목", "새로운 테스트 이슈 내용입니다");

    public static Issue newIssue() {
        return new Issue(DEFAULT_ISSUE_ID, NEW_ISSUE_BODY, BRAD);
    }

    public static Issue newIssue(Long id) {
        return new Issue(id, NEW_ISSUE_BODY, BRAD);
    }

    public static Issue newIssue(String subject, String comment) {
        return new Issue(new IssueBody(subject, comment));
    }

    static {
        issues.add(ISSUE);
        issues.add(ISSUE2);
        issues.add(ISSUE3);
        ISSUE.getComments().addAll(COMMENTS);
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
        softly.assertThat(ISSUE.getComments().contains(COMMENT)).isTrue();
        ISSUE.deleteComment(COMMENT);
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