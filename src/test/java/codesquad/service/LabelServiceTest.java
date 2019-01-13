package codesquad.service;

import codesquad.domain.label.Label;
import codesquad.domain.label.LabelRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.test.BaseTest;

import static org.mockito.Mockito.when;
import static support.test.Fixture.LABEL;
import static support.test.Fixture.LABEL_BODY;

@RunWith(MockitoJUnitRunner.class)
public class LabelServiceTest extends BaseTest {

    @Mock
    LabelRepository labelRepository;

    @InjectMocks
    LabelService labelService;

    @Before
    public void setUp() throws Exception {
        when(labelRepository.save(new Label(LABEL_BODY))).thenReturn(LABEL);
    }

    @Test
    public void create() {
        Label label = labelService.create(LABEL_BODY);
        softly.assertThat(label.hasSameBody(LABEL_BODY)).isTrue();
    }
}