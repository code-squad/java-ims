package codesquad.domain;

import org.springframework.beans.propertyeditors.ReaderEditor;

import static codesquad.domain.UserTest.RED;

public class IssueFixture {
    public static final Issue ISSUE_NULLPOINT_EXCEPTION = new Issue(RED, new IssueBody("Nullpoint Exception", "NullPoint Exception"));
    public static final Issue ISSUE_JSON_PARSE_ERROR = new Issue(RED, new IssueBody("ajax", "json parse error"));
    public static final Issue ISSUE_NOT_FOUND_METHOD = new Issue(RED, new IssueBody("ajax", "405 error"));
    public static final Issue ISSUE_HTTPSTATUS_405 = new Issue(RED, new IssueBody("not found method", "not found method"));

    public static final IssueBody ISSUE_BODY_JSON_PARSE_ERROR = new IssueBody("ajax", "json parse error");
}
