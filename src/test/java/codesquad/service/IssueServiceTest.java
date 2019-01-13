package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.IssueRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.BaseTest;

import java.util.Optional;

import static codesquad.domain.IssueTest.*;
import static codesquad.domain.MilestoneTest.MILESTONE1;
import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(IssueServiceTest.class);

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @Mock
    private MilestoneService milestoneService;

    @Mock
    private UserService userService;

    @InjectMocks
    private IssueService issueService;

    @Before
    public void setUp() throws Exception {
        when(issueRepository.findById(ISSUE1.getId())).thenReturn(Optional.of(ISSUE1));
        when(issueRepository.findById(ISSUE2.getId())).thenReturn(Optional.of(ISSUE2));
        when(issueRepository.findById(ISSUE3.getId())).thenReturn(Optional.of(ISSUE3));
        when(issueRepository.findById(ISSUE4.getId())).thenReturn(Optional.of(ISSUE4));
        when(milestoneService.findById(MILESTONE1.getId())).thenReturn(MILESTONE1);
        when(userService.findById(JAVAJIGI.getId())).thenReturn(JAVAJIGI);
    }

    @Test
    public void update() {
        issueService.update(JAVAJIGI, ISSUE1.getId(), UPDATEDISSUE1);

        softly.assertThat(ISSUE1.getSubject()).isEqualTo(UPDATEDISSUE1.getSubject());
        softly.assertThat(ISSUE1.getComment()).isEqualTo(UPDATEDISSUE1.getComment());
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_no_login() throws Exception {
        issueService.delete(null, ISSUE2.getId());
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_not_owner() throws Exception {
        issueService.delete(SANJIGI, ISSUE2.getId());
    }

    @Test
    public void delete() throws Exception {
        issueService.delete(JAVAJIGI, ISSUE2.getId());
        
        softly.assertThat(ISSUE2.isDeleted()).isTrue();
    }

    @Test(expected = UnAuthorizedException.class)
    public void setMilestone_no_login() {
        issueService.setMilestone(null, ISSUE1.getId(), MILESTONE1.getId());

    }

    @Test(expected = UnAuthorizedException.class)
    public void setMilestone_not_owner() {
        issueService.setMilestone(SANJIGI, ISSUE1.getId(), MILESTONE1.getId());
    }

    @Test
    public void setMilestone() {
        issueService.setMilestone(JAVAJIGI, ISSUE1.getId(), MILESTONE1.getId());

        softly.assertThat(ISSUE1.getMilestone()).isEqualTo(MILESTONE1);
        softly.assertThat(MILESTONE1.getIssues().contains(ISSUE1)).isTrue();
    }

    @Test(expected = Exception.class)
    public void close_already_closed() throws Exception{
        issueService.close(JAVAJIGI, ISSUE4.getId());
    }

    @Test(expected = Exception.class)
    public void close_not_assginee() throws Exception{
        issueService.close(JAVAJIGI, ISSUE2.getId());

        softly.assertThat(ISSUE2.isClosed()).isTrue();
    }

    @Test
    public void close() throws Exception{
        issueService.close(SANJIGI, ISSUE3.getId());

        softly.assertThat(ISSUE3.isClosed()).isTrue();
    }

    @Test(expected = UnAuthorizedException.class)
    public void setAssignee_no_login() {
        issueService.setAssignee(null, ISSUE1.getId(), JAVAJIGI.getId());

    }

    @Test(expected = UnAuthorizedException.class)
    public void setAssignee_not_owner() {
        issueService.setAssignee(SANJIGI, ISSUE1.getId(), JAVAJIGI.getId());
    }

    @Test
    public void setAssignee() {
        issueService.setAssignee(SANJIGI, ISSUE3.getId(), JAVAJIGI.getId());

        softly.assertThat(ISSUE3.getAssignee()).isEqualTo(JAVAJIGI);
    }
}
