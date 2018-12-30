package support.test;

import codesquad.dto.IssueDto;

public class IssueFixture {

    public static final IssueDto SUCCESS_ISSUE = new IssueDto("Subject 5 Character More", "Content 5 Character More");

    public static final IssueDto FAIL_ISSUE_SUBJECT = new IssueDto("less", "Content 5 Character More");

    public static final IssueDto FAIL_ISSUE_CONTENT = new IssueDto("Subject 5 Character More", "less");

    public static final IssueDto FAIL_ISSUE_NOT_CONTENT = new IssueDto("Subject 5 Character More", "");

}
