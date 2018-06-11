package codesquad.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class IssueTest {

    @Test
    public void getStatus_Open() {
        Issue issue = new Issue(1, "title", "content");
        assertEquals("#1 Open", issue.getStatus());
    }

    @Test
    public void getStatus_Closed() {
        Issue issue = new Issue(2, "title", "content");
        issue.setStatus(IssueStatus.CLOSED);
        assertEquals("#2 Closed", issue.getStatus());
    }
}
