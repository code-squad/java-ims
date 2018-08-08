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

    @Test
    public void matchWriter() {
        User writer = new User(1L, "learner", "test1234", "taewon");
        Issue issue = new Issue("사용자 일치 이슈", "코멘트 내용", writer);

        assertThat(issue.matchWriter(writer),is(true));
    }

    @Test
    public void setMilestone() {
        Milestone milestone = new Milestone("사용자 일치 기능");
        Issue issue = new Issue("사용자 일치 이슈", "코멘트 내용");

        issue.registerMilestone(milestone);
        assertThat(issue.toString().contains("사용자 일치 기능"),is(true));
    }
}
