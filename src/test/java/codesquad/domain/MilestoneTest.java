package codesquad.domain;

import codesquad.domain.milestone.Milestone;
import support.test.BaseTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MilestoneTest extends BaseTest {
    public static final Milestone MILESTONE_1 = new Milestone(1L, "MILESTONE_1", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone MILESTONE_2 = new Milestone(2L, "MILESTONE_2", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone MILESTONE_3 = new Milestone(3L, "MILESTONE_3", LocalDateTime.now(), LocalDateTime.now());

    public static final List<Milestone> milestones = new ArrayList<>();
    static {
        milestones.add(MILESTONE_1);
        milestones.add(MILESTONE_2);
        milestones.add(MILESTONE_3);
    }
}
