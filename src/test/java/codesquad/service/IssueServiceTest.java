package codesquad.service;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest {

    @Mock
    IssueRepository issueRepository;

    @InjectMocks
    IssueService issueService;

    private Issue newIssue;
    private User javajigi;

    @Before
    public void setup() {
        newIssue = new Issue("안녕하세요", "코멘트입니다.");
        javajigi = new User("javajigi", "test", "name");
    }

    @Test
    public void read_success() {
        when(issueRepository.findById(anyLong())).thenReturn(Optional.of(newIssue));
        assertThat(issueService.findById(1L)._toIssueDto(), is(newIssue._toIssueDto()));
    }

    @Test (expected = EntityNotFoundException.class)
    public void read_fail() {
        when(issueRepository.findById(anyLong())).thenReturn(Optional.empty());
        issueService.findById(1L);
    }

    @Test
    public void update_success() throws UnAuthenticationException {
        Issue updateTarget = new Issue("this is subject change", "this is comment change");
        newIssue.writtenBy(javajigi);
        when(issueRepository.findById(anyLong())
                .filter(issue -> issue.isWriter(javajigi))).thenReturn(Optional.of(newIssue));
        Issue updateIssue = issueService.update(javajigi, anyLong(), updateTarget._toIssueDto());
        assertThat(updateIssue.getSubject(), is("this is subject change"));
    }

    @Test(expected = UnAuthenticationException.class)
    public void update_fail() throws UnAuthenticationException {
        Issue updateTarget = new Issue("this is subject change", "this is comment change");
        newIssue.writtenBy(javajigi);
        when(issueRepository.findById(anyLong())
                .filter(issue -> issue.isWriter(javajigi))).thenReturn(Optional.empty());
        issueService.update(javajigi, anyLong(), updateTarget._toIssueDto());
    }

    @Test
    public void delete_success() throws UnAuthenticationException {
        newIssue.writtenBy(javajigi);
        when(issueRepository.findById(anyLong())).thenReturn(Optional.of(newIssue));
        issueService.delete(javajigi, anyLong());
        assertThat(newIssue.isDeleted(), is(true));
    }

    @Test(expected = UnAuthenticationException.class)
    public void delete_fail() throws UnAuthenticationException {
        newIssue.writtenBy(javajigi);
        when(issueRepository.findById(anyLong())).thenReturn(Optional.empty());
        issueService.delete(javajigi, anyLong());
    }
}
