package codesquad.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MilestoneTest {
    public static final List<Milestone> MILESTONES = new ArrayList<>();
    public static final Milestone MILESTONE1 = new Milestone(1, "testMilestone1", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone MILESTONE2 = new Milestone(2, "testMilestone2", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone MILESTONE3 = new Milestone(3, "testMilestone3", LocalDateTime.now(), LocalDateTime.now());
    public static final String DATE = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

    static {
        MILESTONES.add(MILESTONE1);
        MILESTONES.add(MILESTONE2);
        MILESTONES.add(MILESTONE3);
    }
}
