package codesquad.domain;

import static codesquad.domain.IssueTest.ISSUE;
import static codesquad.domain.UserTest.BRAD;
import static org.junit.Assert.*;

public class DeleteHistoryTest {
    public static final DeleteHistory DELETE_HISTORY = new DeleteHistory(ContentType.ISSUE, ISSUE.getId(), BRAD);

}