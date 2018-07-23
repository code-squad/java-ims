package codesquad.domain;

import codesquad.dto.IssueDto;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IssueTest {

    @Test
    public void create() {
        Issue issue = new Issue("subject", "comment");
        assertThat(issue.toString().contains("subject")
                &&issue.toString().contains("comment"),is(true));
    }

    @Test
    public void update() {
        User writer = new User("learner", "test1234", "taewon");
        IssueDto issueDto = new IssueDto("subject", "comment");
        Issue issue = issueDto.toIssue(writer);

        IssueDto updateIssueDto = new IssueDto("updateSubject", "updateComment");
        issue.update(updateIssueDto, writer);

        assertThat(issue.toString().contains("updateSubject"), is(true));

    }
}
