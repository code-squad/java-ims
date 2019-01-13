package codesquad.domain;

import org.junit.Test;

import java.time.LocalDateTime;

public class MilestoneTest {

    public static final Milestone FIRST_MILESTONE = new Milestone(1L,"first milestone", "2019-01-10T14:02", "222222-02-22T14:02", UserTest.USER);
    public static final Milestone SECOND_MILESTONE = new Milestone(2L,"second milestone", "2019-01-10T14:02", "222222-02-22T14:02", UserTest.USER);

    @Test
    public void create() {

    }
}
