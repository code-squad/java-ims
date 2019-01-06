package codesquad.service;

import codesquad.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.test.BaseTest;

import java.util.Optional;

import static codesquad.domain.IssueTest.issue1;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest extends BaseTest {
    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueService issueService;

    @Test
    public void show() {
        when(issueRepository.findById(issue1.getId()))
                .thenReturn(Optional.of(issue1));

        Issue issue = issueService.findById(issue1.getId());

        softly.assertThat(issue.isSameComment(issue1)).isTrue();
    }
}
