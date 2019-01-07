package support.test;

import codesquad.domain.Issue;
import codesquad.domain.IssueBody;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class Fixtures {
    public static final Issue ISSUE_NO1 = new Issue(JAVAJIGI,new IssueBody("치명적인 오류발생", "빨리 수정해야한다."));
    public static final Issue ISSUE_NO2 = new Issue(SANJIGI,new IssueBody("유저클래스 리팩토링", "세션유저를만들어보자."));

}
