package codesquad.domain;

import codesquad.domain.history.ContentType;
import codesquad.domain.history.DeleteHistory;

import static codesquad.domain.IssueTest.ISSUE;
import static codesquad.domain.UserTest.BRAD;

public class DeleteHistoryTest {
    public static final DeleteHistory DELETE_HISTORY = new DeleteHistory(ContentType.ISSUE, ISSUE.getId(), BRAD);

}