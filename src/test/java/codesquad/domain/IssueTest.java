package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

import static codesquad.domain.UserTest.BRAD;
import static codesquad.domain.UserTest.JUNGHYUN;

public class IssueTest extends BaseTest {
    public static final long WRONG_ISSUE_ID = 100L;
    public static final List<Issue> issues = new ArrayList<>();
    public static final Issue ISSUE = new Issue(1L, "테스트 이슈1", "테스트 이슈 내용입니다1", BRAD);
    public static final Issue ISSUE2 = new Issue(2L, "테스트 이슈2", "테스트 이슈 내용입니다2", BRAD);
    public static final Issue ISSUE3 = new Issue(3L, "테스트 이슈3", "테스트 이슈 내용입니다3", JUNGHYUN);
    public static final Issue UPDATE_ISSUE = newIssue("업데이트 이슈 제목", "업데이트 이슈 내용입니다");

    public static Issue newIssue(Long id) {
        return new Issue(id, "새로운 테스트 이슈 제목", "새로운 테스트 이슈 내용입니다");
    }

    public static Issue newIssue(String subject, String comment) {
        return new Issue(subject, comment);
    }

    public static Issue newIssue() {
        return new Issue("새로운 테스트 이슈 제목", "새로운 테스트 이슈 내용입니다");
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
        ISSUE.update(BRAD, UPDATE_ISSUE);
        softly.assertThat(ISSUE.hasSameSubjectAndComment(UPDATE_ISSUE));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_같은유저아닐떄() {
        ISSUE.update(JUNGHYUN, UPDATE_ISSUE);
    }
}