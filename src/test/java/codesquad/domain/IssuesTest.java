package codesquad.domain;

import codesquad.EntityAlreadyExistsException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class IssuesTest {

    private Issues issues;

    @Before
    public void setUp() {
        issues = new Issues();
    }

    @Test
    public void addIssue_Issue_Not_Redundant() {
        Issue issue = new Issue("test title", "test content");
        issues.addIssue(issue);
        assertTrue(issues.contains(issue));
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void addIssue_Issue_IS_Redundant() {
        Issue issue = new Issue("test title", "test content");
        issues.addIssue(issue);
        assertTrue(issues.contains(issue));

        issues.addIssue(issue);
    }
}