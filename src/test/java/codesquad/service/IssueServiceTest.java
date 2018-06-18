package codesquad.service;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest {
    private static final Logger log = LoggerFactory.getLogger(IssueServiceTest.class);

    @Mock
    private IssueRepository issueRepo;

    @InjectMocks
    private IssueService issueService;

    @Test
    public void create() {
        Issue issue = validIssue();
        when(issueRepo.save(issue)).thenReturn(issue);

        Issue savedIssue = issueService.create(any(User.class), issue._toDto());
        assertEquals(issue.getContents(), savedIssue.getContents());
        verify(issueRepo, times((1))).save(savedIssue);
        log.debug("saved Issue : {}, {}", savedIssue.getTitle(), savedIssue.getContents());
    }

    @Test
    public void show() {
        Issue issue = validIssue();
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(issue));
        Issue origin = issueService.get(anyLong()).toIssue();

        assertEquals(issue.getContents(), origin.getContents());
    }

    @Test(expected = EntityNotFoundException.class)
    public void show_fail_not_found() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.empty());
        issueService.get(anyLong());
    }

    @Test
    public void edit() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(loginedIssue()));
        IssueDto issueDto = issueService.get(getUser(), anyLong());
        assertThat(issueDto.getContents(), is("test contents"));
    }

    @Test(expected = EntityNotFoundException.class)
    public void edit_fail_not_found() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.empty());
        issueService.get(getUser(), anyLong());
    }

    @Test(expected = UnAuthorizedException.class)
    public void edit_fail_unAuthorized() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.of(loginedIssue()));
        issueService.get(getOtherUser(), anyLong());
    }

    @Test
    public void update() {
    }

    @Test
    public void update_fail_not_found() {
    }

    @Test
    public void update_fail_unAuthorized() {
    }

    @Test
    public void delete() {
    }

    @Test
    public void delete_fail_not_found() {
    }

    @Test
    public void delete_fail_unAuthorized() {
    }

    private Issue validIssue() {
        return new Issue("test title", "test contents");
    }

    private Issue invalidIssue() {
        return new Issue("", "s");
    }

    private Issue loginedIssue() {
        return new Issue("test title", "test contents").writeBy(getUser());
    }

    private User getUser() {
        return new User("colin", "1234", "colin");
    }

    private User getOtherUser() {
        return new User("jinbro", "1234", "jinbro");
    }
}