package codesquad.service;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.milestone.MilestoneRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.test.BaseTest;

import static codesquad.domain.milestone.MilestoneTest.MILESTONE;
import static codesquad.domain.milestone.MilestoneTest.MILESTONE_BODY;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MilestoneServiceTest extends BaseTest {

    @Mock
    MilestoneRepository milestoneRepository;

    @InjectMocks
    MilestoneService milestoneService;

    @Before
    public void setUp() throws Exception {
        when(milestoneRepository.save(MILESTONE)).thenReturn(MILESTONE);
    }

    @Test
    public void create() {
        Milestone savedMileStone = milestoneService.create(MILESTONE_BODY);
        softly.assertThat(savedMileStone.getMilestoneBody().equals(MILESTONE_BODY));
    }
}