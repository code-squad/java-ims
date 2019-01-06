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

import java.util.Optional;

import static codesquad.domain.IssueTest.ISSUE1;
import static codesquad.domain.IssueTest.UPDATEDISSUE1;
import static codesquad.domain.UserTest.JAVAJIGI;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IssueServiceTest extends BaseTest {
    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueService issueService;

    @Before
    public void setUp() throws Exception {
        when(issueRepository.findById(ISSUE1.getId())).thenReturn(Optional.of(ISSUE1));
    }

    @Test
    public void update() {
        issueService.update(JAVAJIGI, ISSUE1.getId(), UPDATEDISSUE1);

        softly.assertThat(ISSUE1.getSubject()).isEqualTo(UPDATEDISSUE1.getSubject());
        softly.assertThat(ISSUE1.getComment()).isEqualTo(UPDATEDISSUE1.getComment());

    }
}
