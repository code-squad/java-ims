package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
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

    @Before
    public void setup() {
        newIssue = new Issue("안녕하세요", "코멘트입니다.");
    }

    @Test
    public void read_success() {
        when(issueRepository.findById(anyLong())).thenReturn(Optional.of(newIssue));
        assertThat(issueService.findById(1L), is(newIssue._toIssueDto()));
    }

    @Test (expected = EntityNotFoundException.class)
    public void read_fail() {
        when(issueRepository.findById(anyLong())).thenReturn(Optional.empty());
        issueService.findById(1L);
    }
}
