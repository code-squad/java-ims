package codesquad.domain.milestone;

import java.time.LocalDateTime;

public class MilestoneTest {
    public static final MilestoneBody MILESTONE_BODY = new MilestoneBody("마일스톤 제목입니다", LocalDateTime.now(), LocalDateTime.now());
    public static final Milestone MILESTONE = new Milestone(MILESTONE_BODY);


}