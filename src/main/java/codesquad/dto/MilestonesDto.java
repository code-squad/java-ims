package codesquad.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MilestonesDto {
    private List<Pair> milestones;

    public MilestonesDto() {
        milestones = new ArrayList<>();
    }

    public MilestonesDto(List<Pair> milestones) {
        this.milestones = milestones;
    }

    public List<Pair> getMilestones() {
        return milestones;
    }

    public void setMilestones(List<Pair> milestones) {
        this.milestones = milestones;
    }

    public void addMilestone(long id, String subject) {
        milestones.add(new Pair(id, subject));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MilestonesDto that = (MilestonesDto) o;
        return Objects.equals(milestones, that.milestones);
    }

    @Override
    public int hashCode() {
        return Objects.hash(milestones);
    }

    @Override
    public String toString() {
        return "MilestonesDto{" +
                "milestones=" + milestones +
                '}';
    }
}
