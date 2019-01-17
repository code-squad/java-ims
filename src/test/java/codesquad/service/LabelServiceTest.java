package codesquad.service;

import codesquad.domain.LabelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.test.BaseTest;

import java.util.Optional;

import static codesquad.domain.IssueTest.ISSUE1;
import static codesquad.domain.LabelTest.*;
import static codesquad.domain.UserTest.JAVAJIGI;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LabelServiceTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(IssueServiceTest.class);

    @Mock
    private LabelRepository labelRepository;

    @Mock
    private IssueService issueService;

    @InjectMocks
    private LabelService labelService;

    @Before
    public void setUp() throws Exception {
        when(issueService.findById(ISSUE1.getId())).thenReturn(ISSUE1);
        when(labelRepository.findById(LABEL2.getId())).thenReturn(Optional.of(LABEL2));
        when(labelRepository.findById(LABEL3.getId())).thenReturn(Optional.of(LABEL3));
        when(labelRepository.findById(UPDATEDLABEL2.getId())).thenReturn(Optional.of(UPDATEDLABEL2));
    }

    @Test
    public void add() throws Exception {
        labelService.add(UPDATEDLABEL2);
        softly.assertThat(labelRepository.findById(UPDATEDLABEL2.getId()).get().getName()).isEqualTo(UPDATEDLABEL2.getName());
    }

    @Test
    public void update() throws Exception{
        labelService.update(JAVAJIGI, LABEL2.getId(), UPDATEDLABEL2);
        softly.assertThat(labelRepository.findById(LABEL2.getId()).get().getName()).isEqualTo(UPDATEDLABEL2.getName());
    }

    @Test
    public void setIssues() {
        labelService.setIssue(ISSUE1.getId(), LABEL3.getId());
        softly.assertThat(labelRepository.findById(LABEL3.getId()).get().getIssues().contains(ISSUE1)).isTrue();
    }
}
