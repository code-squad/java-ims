package codesquad.domain;

import java.time.LocalDateTime;

public class MilestoneFixture {
    public static final Milestone MILESTONE_1 = new Milestone("subject",
            LocalDateTime.of(2018, 9, 10, 10, 00),
            LocalDateTime.of(2019, 1, 31, 18, 0));
}
