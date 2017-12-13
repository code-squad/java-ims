package codesquad.model;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class IssueTest {
	private Issue issue;

	@Before
	public void before() {
		issue = new Issue();
		issue.setComment("comment");
		issue.setSubject("subject");
	}

	@Test
	public void testUpdateSubject() {
		assertEquals(issue.getSubject(), "subject");
		issue.updateSubject("update subject");
		assertEquals(issue.getSubject(), "update subject");
	}

	@Test
	public void testUpdateComment() {
		assertEquals(issue.getComment(), "comment");
		issue.updateComment("update comment");
		assertEquals(issue.getComment(), "update comment");
	}
}