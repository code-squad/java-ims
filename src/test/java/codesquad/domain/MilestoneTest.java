package codesquad.domain;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class MilestoneTest {
	public static final User JAVAJIGI = new User(1L, "javajigi", "testtest", "자바지기");
	public static final User SARAM = new User(2L, "saram4030", "testtest", "이끼룩");

	@Test
	public void addIssue() {
		Milestone milestone = new Milestone("마일스톤주제", LocalDateTime.now(), LocalDateTime.now().plusDays(7));
		Issue issue = new Issue("이슈주제", "이슈코멘트", SARAM);

		int issuesSize = milestone.getIssuesSize();
		milestone.addIssue(issue);

		assertThat(milestone.getIssuesSize(), is(issuesSize + 1));
	}
}
