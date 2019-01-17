package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static codesquad.domain.IssueTest.ISSUE1;

public class MilestoneTest extends BaseTest {
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

    @Test
    public void addIssue() {
        MILESTONE3.addIssue(ISSUE1);

        softly.assertThat(MILESTONE3.getIssues().size()).isEqualTo(1);
        softly.assertThat(MILESTONE3.getIssues().contains(ISSUE1)).isTrue();
        softly.assertThat(ISSUE1.getMilestone()).isEqualTo(MILESTONE3);
    }
}
