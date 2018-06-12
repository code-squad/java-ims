package codesquad.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class IssueStatusTest {

    @Test
    public void toStringTest_OPEN() {
        IssueStatus status = IssueStatus.OPEN;
        assertEquals("Open", status.toString());
    }

    @Test
    public void toStringTest_CLOSED() {
        IssueStatus status = IssueStatus.CLOSED;
        assertEquals("Closed", status.toString());
    }
}