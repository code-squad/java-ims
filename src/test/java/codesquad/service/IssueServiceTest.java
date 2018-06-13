package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

        Issue savedIssue = issueService.create(any(User.class), issue.toDto());
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

    @Test (expected = EntityNotFoundException.class)
    public void show_fail_not_found() {
        when(issueRepo.findById(anyLong())).thenReturn(Optional.empty());
        issueService.get(anyLong());
    }

    @Test
    public void edit() {
    }

    @Test
    public void edit_fail_not_found() {
    }

    @Test
    public void edit_fail_unAuthorized() {
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
}