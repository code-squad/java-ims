package codesquad.domain;

import codesquad.domain.milestone.Milestone;
import support.test.BaseTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MilestoneTest extends BaseTest {
    public static final Milestone milestone1 = new Milestone(1L, "milestone1", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone milestone2 = new Milestone(2L, "milestone2", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone milestone3 = new Milestone(3L, "milestone3", LocalDateTime.now(), LocalDateTime.now());

    public static final List<Milestone> milestones = new ArrayList<>();
    static {
        milestones.add(milestone1);
        milestones.add(milestone2);
        milestones.add(milestone3);
    }
}
