package codesquad.domain;

import java.time.LocalDateTime;

public class MilestoneFixture {
    public static final Milestone PROGRAMMING = new Milestone("Programming",
            LocalDateTime.of(2018, 12, 1, 0, 0),
            LocalDateTime.of(2018, 12, 31, 0, 0));

    public static final Milestone CODESQUAD = new Milestone("CodeSquad",
            LocalDateTime.of(2018, 9, 10, 10, 0),
            LocalDateTime.of(2019, 1, 31, 18, 0));
}
