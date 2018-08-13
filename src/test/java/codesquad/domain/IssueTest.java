package codesquad.domain;

import codesquad.dto.IssueDto;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IssueTest {
    // 단위테스트에서 직접적으로 상태가 변하는 객체는 각 메소드에 독립적으로 넣고, 비교적 덜 변하는 공통 객체는 전역변수로 뺀다
    final static private User writer = new User(3L, "learner", "password", "taewon");

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

        issue.setMilestone(milestone);
        assertThat(issue.toString().contains("사용자 일치 기능"), is(true));
    }

    @Test
    public void setAssignee() {
        User user = new User(1L, "learner", "test1234", "taewon");
        Issue issue = new Issue("사용자 일치 이슈", "코멘트 내용");

//        issue.registerAssignee(user);
//        assertThat(issue.toString().contains("learner"), is(true));
    }

    @Test
    public void setLabel() {
        Label label = new Label("라벨");
        Issue issue = new Issue("라벨 일치 이슈", "코멘트 내용");

        issue.registerLabel(label);
        assertThat(issue.toString().contains("라벨"),is(true));
    }
}
