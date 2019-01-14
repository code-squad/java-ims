package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import support.test.BaseTest;

import java.util.Optional;

import static codesquad.domain.IssueFixture.ISSUE_NULLPOINT_EXCEPTION;
import static codesquad.domain.MilestoneFixture.PROGRAMMING;
import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.RED;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest extends BaseTest {
    private static final Logger log = getLogger(IssueServiceTest.class);

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private MilestoneRepository milestoneRepository;

    @InjectMocks
    private IssueService issueService;

    @Test
    public void delete_issue() {
        Issue issue = ISSUE_NULLPOINT_EXCEPTION;
        when(issueRepository.findById(issue.getId())).thenReturn(Optional.of(issue));

        issueService.delete(RED, issue.getId());
        softly.assertThat(issueRepository.findById(issue.getId()).get().isDeleted()).isTrue();
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_issue_other_writer() {
        Issue issue = ISSUE_NULLPOINT_EXCEPTION;
        when(issueRepository.findById(issue.getId())).thenReturn(Optional.of(issue));

        issueService.delete(JAVAJIGI, issue.getId());
    }

    @Test
    public void addMilestone() {
        Issue issue = ISSUE_NULLPOINT_EXCEPTION;
        Milestone milestone = PROGRAMMING;

        when(issueRepository.findById(issue.getId())).thenReturn(Optional.of(issue));
        when(milestoneRepository.findById(milestone.getId())).thenReturn(Optional.of(milestone));

        issueService.addMilestone(issue.getId(), milestone.getId());
        softly.assertThat(issue.getMilestone()).isEqualTo(milestone);
    }
}
