package codesquad.domain;

import org.junit.Test;

import java.time.LocalDateTime;

public class MilestoneTest {

    public static final Milestone FIRST_MILESTONE = new Milestone(1L,"first milestone", LocalDateTime.now(), LocalDateTime.now(), UserTest.USER);
    public static final Milestone SECOND_MILESTONE = new Milestone(2L,"second milestone", LocalDateTime.now(), LocalDateTime.now(), UserTest.USER);

    @Test
    public void create() {

    }
}
