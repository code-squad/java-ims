package codesquad.domain;

import static org.junit.Assert.*;

public class IssueTest {
    public static final long WRONG_ISSUE_ID = 100L;
    public static final Issue issue = new Issue(1L, "테스트 이슈1", "테스트 이슈 내용입니다1");
    public static final Issue issue2 = new Issue(2L, "테스트 이슈2", "테스트 이슈 내용입니다2");
    public static final Issue issue3 = new Issue(3L, "테스트 이슈3", "테스트 이슈 내용입니다3");

}