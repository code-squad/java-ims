package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.domain.issue.Issue;
import codesquad.domain.issue.IssueBody;
import org.junit.Test;
import org.slf4j.Logger;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

import static codesquad.domain.UserTest.BRAD;
import static codesquad.domain.UserTest.JUNGHYUN;
import static org.slf4j.LoggerFactory.getLogger;

public class IssueTest extends BaseTest {
    private static final Logger log = getLogger(IssueTest.class);

    public static final long WRONG_ISSUE_ID = 100L;
    public static final List<Issue> issues = new ArrayList<>();
    public static final IssueBody ISSUE_BODY = IssueBody.of("테스트 이슈1", "테스트 이슈 내용입니다1");
    public static final Issue ISSUE = new Issue(1L, ISSUE_BODY, BRAD);
    public static final IssueBody ISSUE_BODY2 = IssueBody.of("테스트 이슈2", "테스트 이슈 내용입니다2");
    public static final Issue ISSUE2 = new Issue(2L, ISSUE_BODY2, BRAD);
    public static final IssueBody ISSUE_BODY3 = IssueBody.of("테스트 이슈3", "테스트 이슈 내용입니다3");
    public static final Issue ISSUE3 = new Issue(3L, ISSUE_BODY3, JUNGHYUN);
    public static final IssueBody UPDATE_ISSUE_BODY = IssueBody.of("업데이트 이슈 제목", "업데이트 이슈 내용입니다");
    public static final IssueBody NEW_ISSUE_BODY = IssueBody.of("새로운 테스트 이슈 제목", "새로운 테스트 이슈 내용입니다");

    public static Issue newIssue(Long id) {
        return new Issue(id, NEW_ISSUE_BODY);
    }

    public static Issue newIssue(String subject, String comment) {
        return new Issue(IssueBody.of(subject, comment));
    }

    public static Issue newIssue() {
        return new Issue();
    }

    static {
        issues.add(ISSUE);
        issues.add(ISSUE2);
        issues.add(ISSUE3);
    }

    @Test
    public void wirttenBy() {
        ISSUE.writtenBy(BRAD);
        softly.assertThat(ISSUE.isOwner(BRAD)).isEqualTo(true);
    }

    @Test
    public void update() {
        ISSUE.update(BRAD, UPDATE_ISSUE_BODY);
        softly.assertThat(ISSUE.hasSameIssueBody(UPDATE_ISSUE_BODY));
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
}