package codesquad.service;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import codesquad.dto.IssueDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueServiceTest.class);

    @Mock
    private IssueRepository issueRepo;

    @Mock
    private DeleteHistoryRepository deleteHistoryRepo;

    @InjectMocks
    private IssueService issueService;

    @Test
    public void create() {
        Issue issue = validIssue();
        when(issueRepo.save(issue)).thenReturn(issue);

        Issue savedIssue = issueService.create(any(User.class), issue._toDto());
        assertEquals(issue.getComment(), savedIssue.getComment());
        verify(issueRepo, times((1))).save(savedIssue);
        log.debug("saved Issue : {}, {}", savedIssue.getSubject(), savedIssue.getComment());
    }

    @Test
    public void show() {
        Issue issue = validIssue();
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(issue));
        Issue origin = issueService.findById(anyLong());

        assertEquals(issue.getComment(), origin.getComment());
    }

    @Test(expected = EntityNotFoundException.class)
    public void show_fail_not_found() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.empty());
        issueService.findById(anyLong());
    }

    @Test
    public void edit() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(loginedIssue()));
        Issue issue = issueService.findById(getUser(), anyLong());
        assertThat(issue.getComment(), is("test contents"));
    }

    @Test(expected = EntityNotFoundException.class)
    public void edit_fail_not_found() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.empty());
        issueService.findById(getUser(), anyLong());
    }

    @Test(expected = UnAuthorizedException.class)
    public void edit_fail_unAuthorized() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(loginedIssue()));
        issueService.findById(getOtherUser(), anyLong());
    }

    @Test
    public void update() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(loginedIssue()));
        IssueDto updateIssueDto = modifiedIssueDto();
        Issue updated = issueService.update(getUser(), anyLong(), updateIssueDto);
        assertTrue(updated.getComment().startsWith("modify"));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_fail_unAuthorized() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(loginedIssue()));
        issueService.update(getOtherUser(), anyLong(), modifiedIssueDto());
    }

    @Test
    public void delete() throws Exception {
        Issue target = loginedIssue();
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(target));
        when(deleteHistoryRepo.save(any())).thenReturn(target.delete(getUser()));
        issueService.delete(getUser(), anyLong());
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_fail_unAuthorized() throws Exception {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(loginedIssue()));
        issueService.delete(getOtherUser(), anyLong());
    }

    @Test
    public void selectMilestone() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(loginedIssue()));
        issueService.selectMilestone(anyLong(), getMilestone());
    }

    @Test(expected = EntityNotFoundException.class)
    public void selectMilestone_fail_not_exist_issue() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.empty());
        issueService.selectMilestone(anyLong(), getMilestone());
    }

    @Test
    public void selectAssignee() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(loginedIssue()));
        issueService.selectAssignee(anyLong(), getOtherUser());
    }

    @Test(expected = EntityNotFoundException.class)
    public void selectAssignee_fail_not_exist_issue() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.empty());
        issueService.selectAssignee(anyLong(), getOtherUser());
    }

    private Issue validIssue() {
        return new Issue("test subject", "test comment");
    }

    private Issue invalidIssue() {
        return new Issue("", "s");
    }

    private IssueDto modifiedIssueDto() {
        return new Issue("modify subject", "modify comment")._toDto();
    }

    private Issue loginedIssue() {
        return new Issue("test subject", "test contents").writeBy(getUser());
    }

    private User getUser() {
        return new User("colin", "1234", "colin");
    }

    private User getOtherUser() {
        return new User("jinbro", "1234", "jinbro");
    }

    private Milestone getMilestone() {
        return new Milestone("test subject", LocalDateTime.now(), LocalDateTime.now());
    }
}