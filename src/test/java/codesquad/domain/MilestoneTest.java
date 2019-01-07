package codesquad.domain;

import org.junit.Test;
import support.test.BaseTest;

import static support.test.Fixtures.ISSUE_NO1;
import static support.test.Fixtures.ISSUE_NO2;

public class MilestoneTest extends BaseTest {

    //Todo 중복재거 및 픽스처 사용시 다른 태스트와 종속되는거 분리시키자.
    @Test
    public void create() {
        String startDate = "";
        String endDate = "";
        Milestone milestone = new Milestone(startDate, endDate);

    }

    @Test
    public void addIssue() {
        String startDate = "";
        String endDate = "";
        Milestone milestone = new Milestone(startDate, endDate);
        milestone.addIssue(ISSUE_NO1);
        milestone.addIssue(ISSUE_NO2);
        softly.assertThat(ISSUE_NO1.getMilestone()).isEqualTo(milestone);
        softly.assertThat(ISSUE_NO2.getMilestone()).isEqualTo(milestone);
        softly.assertThat(milestone.getIssues().size()).isEqualTo(2);
    }

    @Test
    public void openIssue() {
        String startDate = "";
        String endDate = "";
        Milestone milestone = new Milestone(startDate, endDate);
        milestone.addIssue(ISSUE_NO1);
        softly.assertThat(milestone.getOpenIssue()).isEqualTo(1L);
        ISSUE_NO1.toClose();
        softly.assertThat(milestone.getOpenIssue()).isEqualTo(0L);
    }

    @Test
    public void closeIssue() {
        String startDate = "";
        String endDate = "";
        Milestone milestone = new Milestone(startDate, endDate);
        milestone.addIssue(ISSUE_NO2);
        softly.assertThat(milestone.getCloseIssue()).isEqualTo(0L);
        ISSUE_NO2.toClose();
        softly.assertThat(milestone.getCloseIssue()).isEqualTo(1L);
    }

}
