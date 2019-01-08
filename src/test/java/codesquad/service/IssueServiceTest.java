package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.UserTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import support.test.BaseTest;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest extends BaseTest {
    private static final Logger log = getLogger(IssueServiceTest.class);
    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueService issueService;


    public Issue newIssue() {
        Issue newIssue = new Issue("제목입니다.", "내용입니다.", UserTest.JAVAJIGI);
        return newIssue;
    }

    public Issue updatedIssue() {
        Issue updatedIssue = new Issue("업데이트 제목", "업데이트 내용",null);
        return updatedIssue;
    }

    @Before
    public void setUp() throws Exception {
        when(issueRepository.findById(newIssue().getId())).thenReturn(Optional.of(newIssue()));
    }

    @Test(expected = UnAuthenticationException.class)
    public void create_no_login() {
        issueService.create(null, newIssue()._toIssueDto());
    }

    @Test
    public void update_issue() {
        Issue updatedIssue = issueService.update(UserTest.JAVAJIGI, newIssue().getId(), updatedIssue()._toIssueDto());
        softly.assertThat(updatedIssue._toIssueDto().getComment()).isEqualTo("업데이트 내용");
    }

    @Test(expected = UnAuthenticationException.class)
    public void update_issue_no_login() {
        issueService.update(null, newIssue().getId(), updatedIssue()._toIssueDto());
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_issue_by_other_user() {
        issueService.update(UserTest.SANJIGI, newIssue().getId(), updatedIssue()._toIssueDto());
    }

    @Test
    public void delete_issue() {
        Issue deletedIssue = issueService.deleteIssue(UserTest.JAVAJIGI, newIssue().getId());
        log.debug("de:{}", deletedIssue._toIssueDto());
        softly.assertThat(deletedIssue._toIssueDto().isDeleted()).isTrue();
    }

    @Test(expected = UnAuthenticationException.class)
    public void delete_issue_by_no_login_user() {
        issueService.deleteIssue(null, newIssue().getId());
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_issue_by_other_user() {
        issueService.deleteIssue(UserTest.SANJIGI, newIssue().getId());
    }
}
