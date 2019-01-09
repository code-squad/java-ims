package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.domain.IssueRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.test.BaseTest;

import java.util.Optional;

import static codesquad.domain.IssueTest.*;
import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest extends BaseTest {
    @Mock
    private IssueRepository issueRepository;

    @Mock
    private DeleteHistoryService deleteHistoryService;

    @Mock
    private MilestoneService milestoneService;

    @InjectMocks
    private IssueService issueService;

    @Before
    public void setUp() throws Exception {
        when(issueRepository.findById(ISSUE1.getId())).thenReturn(Optional.of(ISSUE1));
        when(issueRepository.findById(ISSUE2.getId())).thenReturn(Optional.of(ISSUE2));
        when(issueRepository.findById(ISSUE4.getId())).thenReturn(Optional.of(ISSUE4));
    }

    @Test
    public void update() {
        issueService.update(JAVAJIGI, ISSUE1.getId(), UPDATEDISSUE1);

        softly.assertThat(ISSUE1.getSubject()).isEqualTo(UPDATEDISSUE1.getSubject());
        softly.assertThat(ISSUE1.getComment()).isEqualTo(UPDATEDISSUE1.getComment());
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_no_login() throws Exception {
        issueService.delete(null, ISSUE2.getId());
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_not_owner() throws Exception {
        issueService.delete(SANJIGI, ISSUE2.getId());
    }

    @Test
    public void delete() throws Exception {
        issueService.delete(JAVAJIGI, ISSUE2.getId());
        
        softly.assertThat(ISSUE2.isDeleted()).isTrue();
    }

//      어떻게 테스트해야하는가?
//    @Test
//    public void setMilestone() {
//        issueService.setMilestone(ISSUE1.getId(), MILESTONE1.getId());
//
//        softly.assertThat(ISSUE1.getMilestone()).isEqualTo(MILESTONE1);
//        softly.assertThat(MILESTONE1.getIssues().contains(ISSUE1)).isTrue();
//    }

    @Test(expected = RuntimeException.class)
    public void close_already_closed() {
        issueService.close(ISSUE4.getId());
    }

    @Test
    public void close() {
        issueService.close(ISSUE1.getId());

        softly.assertThat(ISSUE1.isClosed()).isTrue();
    }
}
