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
                && issue.toString().contains("comment"), is(true));
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

        assertThat(issue.matchWriter(writer), is(true));
    }

    @Test
    public void setMilestone() {
        Milestone milestone = new Milestone("사용자 일치 기능");
        Issue issue = new Issue("사용자 일치 이슈", "코멘트 내용");

        issue.registerMilestone(milestone);
        assertThat(issue.toString().contains("사용자 일치 기능"), is(true));
    }

    @Test
    public void setAssignee() {
        User writer = new User(3L, "learner", "test1234", "taewon");
        Issue issue = new Issue(4L, "사용자 일치 이슈", "이슈 내용", writer);

        issue.registerAssignee(writer.getId());
        assertThat(issue.toString().contains("userId=3"), is(true));
        assertThat(issue.toString().contains("issueId=4"), is(true));
    }

    @Test
    public void setLabel() {
        Label label = new Label("라벨");
        Issue issue = new Issue("라벨 일치 이슈", "코멘트 내용");

        issue.registerLabel(label);
        assertThat(issue.toString().contains("라벨"),is(true));
    }
}
