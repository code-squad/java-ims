package codesquad.service;

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

import static codesquad.domain.IssueTest.ISSUE;
import static codesquad.domain.UserTest.BRAD;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest extends BaseTest {

    @Mock
    private IssueRepository issueRepository;

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
        issueService.findById(1000L);
    }

    @Test
    public void create() {
        Issue createdIssue = issueService.create(BRAD, ISSUE);
        softly.assertThat(createdIssue.isOwner(BRAD)).isEqualTo(true);
    }
}