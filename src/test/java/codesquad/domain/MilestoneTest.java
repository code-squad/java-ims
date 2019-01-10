package codesquad.domain;

import codesquad.domain.milestone.Milestone;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class MilestoneTest extends BaseTest {
    public static final Milestone milestone1 = new Milestone(1L, "milestone1", "2019-01-05", "2019-01-09");
    public static final Milestone milestone2 = new Milestone(2L, "milestone2", "2019-01-06", "2019-01-12");
    public static final Milestone milestone3 = new Milestone(3L, "milestone3", "2019-01-07", "2019-01-10");

    public static final List<Milestone> milestones = new ArrayList<>();
    static {
        milestones.add(milestone1);
        milestones.add(milestone2);
        milestones.add(milestone3);
    }
}
