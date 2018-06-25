package codesquad.service;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.MilestoneRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MilestoneServiceTest {

    @Mock
    private MilestoneRepository milestoneRepo;

    @InjectMocks
    private MilestoneService milestoneService;

    private Milestone milestone;
    private Issue issue;

    @Before
    public void setUp() throws Exception {
        milestone = new Milestone("test subject", LocalDateTime.now(), LocalDateTime.now());
        issue = new Issue("test subject", "test comment");
    }

    @Test
    public void get() {
        when(milestoneRepo.findById(anyLong())).thenReturn(Optional.of(milestone));
        Milestone milestone = milestoneService.findAll(anyLong());
        assertThat(milestone.getSubject(), is("test subject"));
    }

    @Test(expected = EntityNotFoundException.class)
    public void get_fail_not_exist_entity() {
        when(milestoneRepo.findById(anyLong())).thenReturn(Optional.empty());
        milestoneService.findAll(anyLong());
    }

    @Test(expected = EntityNotFoundException.class)
    public void addIssue_fail_milestone_not_exist() {
        when(milestoneRepo.findById(anyLong())).thenReturn(Optional.empty());
        milestoneService.addIssue(anyLong(), issue);
    }
}