package codesquad.domain;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThat;

public class IssueTest {
	private Issue issue;
	private Issue updateIssue;
	private User user;
	private User anotherUser;

	@Before
	public void setup() {
		user = new User("userId", "password", "name");
		anotherUser = new User("anotherUser", "1234", "another");
		issue = new Issue("testSubject", "testComment");
		issue.writeBy(user);
		updateIssue = new Issue("updateSubject", "updateComment");
		updateIssue.writeBy(user);
	}

	@Test
	public void isOwner_true() {
		assertTrue(issue.isOwner(user));
	}

	@Test
	public void isOwner_false() {
		assertTrue(!issue.isOwner(anotherUser));
	}

	@Test
	public void update_owner() {
		issue.update(user, updateIssue);
		assertTrue(issue.equals(updateIssue));
	}

	@Test(expected = UnAuthorizedException.class)
	public void update_not_owner() {
		issue.update(anotherUser, updateIssue);
	}
}
