package codesquad.service;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.milestone.MilestoneRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import support.test.BaseTest;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static codesquad.domain.milestone.MilestoneTest.*;
import static org.mockito.Mockito.when;
import static org.slf4j.LoggerFactory.getLogger;

@RunWith(MockitoJUnitRunner.class)
public class MilestoneServiceTest extends BaseTest {
    private static final Logger log = getLogger(MilestoneServiceTest.class);

    @Mock
    MilestoneRepository milestoneRepository;

    @InjectMocks
    MilestoneService milestoneService;

    @Before
    public void setUp() throws Exception {
        when(milestoneRepository.save(new Milestone(MILESTONE_BODY))).thenReturn(MILESTONE);
        when(milestoneRepository.findById(MILESTONE.getId())).thenReturn(Optional.of(MILESTONE));
    }

    @Test
    public void create() {
        Milestone savedMileStone = milestoneService.create(MILESTONE_BODY);
        softly.assertThat(savedMileStone.getMilestoneBody().equals(MILESTONE_BODY));
    }

    @Test
    public void findById() {
        softly.assertThat(milestoneService.findById(MILESTONE.getId())).isEqualTo(MILESTONE);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findById_찾는값없을떄() {
        softly.assertThat(milestoneService.findById(WRONG_MILESTONE_ID)).isEqualTo(MILESTONE);
    }
}