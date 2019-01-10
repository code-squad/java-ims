package support.test;

import codesquad.domain.Content;
import codesquad.dto.IssueDto;

public class IssueFixture {

    public static final IssueDto SUCCESS_ISSUE_JAVAJIGI = new IssueDto("Success Subject", "Success Comment", UserFixture.JAVAJIGI);

    public static final IssueDto SUCCESS_ISSUE_DOBY = new IssueDto("Success Subject", "Success Comment", UserFixture.DOBY);

    public static final IssueDto FAIL_ISSUE = new IssueDto("", "Success Comment", UserFixture.JAVAJIGI);

}
