package codesquad.domain;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class IssueTest {
    public static final Issue ISSUE_JAVAJIGI = new Issue(10L, "javajigi's issue", "javajigi contents", JAVAJIGI);
    public static final Issue ISSUE_SANJIGI = new Issue(11L, "sanjigi's issue", "sanjigi contents", SANJIGI);

}
