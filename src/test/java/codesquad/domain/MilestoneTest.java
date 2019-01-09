package codesquad.domain;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.milestone.MilestoneBody;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MilestoneTest {
    public static final long WRONG_MILESTONE_ID = 100L;
    public static final MilestoneBody MILESTONE_BODY = new MilestoneBody("Milestone1", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone MILESTONE = new Milestone(1L, MILESTONE_BODY);
    public static final MilestoneBody MILESTONE_BODY2 = new MilestoneBody("Milestone2", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone MILESTONE2 = new Milestone(2L, MILESTONE_BODY2);
    public static final MilestoneBody MILESTONE_BODY3 = new MilestoneBody("Milestone3", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone MILESTONE3 = new Milestone(3L, MILESTONE_BODY3);

    public static final List<Milestone> MILESTONES = new ArrayList<>();

    static {
        MILESTONES.add(MILESTONE);
        MILESTONES.add(MILESTONE2);
        MILESTONES.add(MILESTONE3);
    }

}