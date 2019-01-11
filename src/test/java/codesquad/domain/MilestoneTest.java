package codesquad.domain;

import org.junit.Test;

import static codesquad.domain.MilestoneFixture.MILESTONE_1;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MilestoneTest {

    @Test
    public void getDueTo() {
        Milestone m = MILESTONE_1;
        assertThat(m.getDueBy()).isEqualTo("Jan 31, 2019");
    }
}
