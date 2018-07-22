package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.dto.IssueDto;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class IssueDtoTest {
    @Test
    public void create() {
        String subject = "이슈 제목";
        String comment = "이슈 내용";

        IssueDto issueDto = new IssueDto(subject, comment);
        Issue issue = issueDto.toIssue();

        assertThat(issue.toString().contains("이슈 제목"),is(true));
    }
}
