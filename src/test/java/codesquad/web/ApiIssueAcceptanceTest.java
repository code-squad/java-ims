package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.domain.UserTest;
import codesquad.dto.IssueDto;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;

public class ApiIssueAcceptanceTest extends AcceptanceTest {
    @Test
    public void create() {
        Issue issueOne = new Issue("subject","comment");
        String location = createResource("/api/issues",issueOne);
        ResponseEntity<Issue> responseEntity = template.getForEntity(location, Issue.class);
//        IssueDto issue = template().getForObject(location, IssueDto.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        softly.assertThat(responseEntity.getBody()).isNotNull();
    }



    private IssueDto createIssueDto(User user) {
        return new IssueDto("subject", "comment", user);
    }
}
