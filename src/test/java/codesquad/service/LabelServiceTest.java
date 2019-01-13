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

import static codesquad.domain.LabelTest.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LabelServiceTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(IssueServiceTest.class);

    @Mock
    private LabelRepository labelRepository;

    @InjectMocks
    private LabelService labelService;

    @Before
    public void setUp() throws Exception {
        when(labelRepository.findById(LABEL2.getId())).thenReturn(Optional.of(LABEL2));
        when(labelRepository.findById(UPDATEDLABEL2.getId())).thenReturn(Optional.of(UPDATEDLABEL2));
    }

    @Test
    public void add() throws Exception {
        labelService.add(UPDATEDLABEL2);
        softly.assertThat(labelRepository.findById(UPDATEDLABEL2.getId()).get().getName()).isEqualTo(UPDATEDLABEL2.getName());
    }

    @Test
    public void update() {
        labelService.update(LABEL2.getId(), UPDATEDLABEL2);
        softly.assertThat(labelRepository.findById(LABEL2.getId()).get().getName()).isEqualTo(UPDATEDLABEL2.getName());
    }

    @Test
    public void delete() throws Exception {
        labelService.delete(LABEL1.getId());
        softly.assertThat(labelRepository.findById(LABEL1.getId())).isEmpty();
    }
}
