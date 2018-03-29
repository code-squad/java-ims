package codesquad.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import codesquad.dto.IssueDto;

public class IssueTest {
	private Issue updateIssue = new Issue("update issue", "update contents");
	
	private User loginUser = new User(1L, "ksm0814", "k5696", "link");
	private User otherUser = new User(2L, "test", "pass", "성민");
	
	public Issue create(String title, String contents) {
		return new Issue(title, contents);
	}
	@Test
	public void update() throws Exception{
		Issue issue = create("new issue", "new contents");
		issue.writeBy(loginUser);
		IssueDto updateIssueDto = updateIssue.toIssueDto();
		issue.update(loginUser, updateIssueDto);
		assertThat(issue.getTitle(), is(updateIssue.getTitle()));
		assertThat(issue.getContents(), is(updateIssue.getContents()));
	}
	
	@Test
	public void update_다른사람() throws Exception{
		Issue issue = create("new issue", "new contents");
		issue.writeBy(loginUser);
		IssueDto updateOtherIssueDto = updateIssue.toIssueDto();
		try {
			issue.update(otherUser, updateOtherIssueDto);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		finally {
			assertTrue(!issue.getTitle().equals(updateIssue.getTitle()));
			assertTrue(!issue.getContents().equals(updateIssue.getContents()));
		}
	}
	
	@Test
	public void delete() throws Exception{
		Issue issue = create("new issue", "new contents");
		issue.writeBy(loginUser);
		issue.delete(loginUser);
		assertTrue(issue.isDeleted());;
	}
	
	@Test
	public void delete_다른사람() throws Exception{
		Issue issue = create("new issue", "new contents");
		issue.writeBy(loginUser);
		try {
			issue.delete(otherUser);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		finally {
			assertTrue(!issue.isDeleted());;
		}
	}
	
}
