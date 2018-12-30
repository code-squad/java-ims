package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.DeleteHistoryRepository;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.test.BaseTest;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static codesquad.domain.IssueTest.*;
import static codesquad.domain.UserTest.BRAD;
import static codesquad.domain.UserTest.JUNGHYUN;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest extends BaseTest {

    @Mock
    private IssueRepository issueRepository;

    @Mock
    private DeleteHistoryRepository deleteHistoryRepository;

    @InjectMocks
    private IssueService issueService;

    @Before
    public void setUp() throws Exception {
        when(issueRepository.findById(ISSUE.getId())).thenReturn(Optional.of(ISSUE));
        when(issueRepository.save(ISSUE)).thenReturn(ISSUE);
    }

    @Test
    public void show() {
        Issue savedIssue = issueService.findById(ISSUE.getId());
        softly.assertThat(savedIssue.hasSameSubjectAndComment(ISSUE)).isEqualTo(true);
    }

    @Test(expected = EntityNotFoundException.class)
    public void show_없는이슈_찾을때() {
        issueService.findById(WRONG_ISSUE_ID);
    }

    @Test
    public void create() {
        Issue createdIssue = issueService.create(BRAD, ISSUE);
        softly.assertThat(createdIssue.isOwner(BRAD)).isEqualTo(true);
    }

    @Test
    public void update() {
        Issue updatedIssue = issueService.update(BRAD, ISSUE.getId(), UPDATE_ISSUE);
    }

    @Test(expected = EntityNotFoundException.class)
    public void update_없는이슈() {
        issueService.update(BRAD, WRONG_ISSUE_ID, UPDATE_ISSUE);
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_다른유저() {
        issueService.update(JUNGHYUN, ISSUE.getId(), UPDATE_ISSUE);
    }

    @Test
    public void findById() {
        Issue issue = issueService.findById(BRAD, ISSUE.getId());
        softly.assertThat(issue.equals(ISSUE)).isTrue();
    }

    @Test(expected = UnAuthorizedException.class)
    public void findById_다른유저() {
        Issue issue = issueService.findById(JUNGHYUN, ISSUE.getId());
    }

    @Test
    public void delete() {
        issueService.deleteIssue(BRAD, ISSUE.getId());
        softly.assertThat(ISSUE.isDeleted()).isEqualTo(true);
    }

    @Test(expected = UnAuthorizedException.class)
    public void delete_다른유저() {
        issueService.deleteIssue(JUNGHYUN, ISSUE.getId());
        softly.assertThat(ISSUE.isDeleted()).isEqualTo(false);
    }
}