package support.test;

import codesquad.domain.Content;
import codesquad.dto.IssueDto;

public class IssueFixture {

    public static final IssueDto SUCCESS_ISSUE_JAVAJIGI = new IssueDto(ContentFixture.SUCCESS_CONTENT, UserFixture.JAVAJIGI);

    public static final IssueDto SUCCESS_ISSUE_DOBY = new IssueDto(ContentFixture.SUCCESS_CONTENT, UserFixture.DOBY);

    public static final IssueDto FAIL_ISSUE = new IssueDto(ContentFixture.FAIL_CONTENT, UserFixture.JAVAJIGI);

}
