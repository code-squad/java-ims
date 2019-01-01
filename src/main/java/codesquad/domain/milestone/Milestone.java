package codesquad.domain.milestone;

import support.domain.AbstractEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Milestone extends AbstractEntity {

    @Embedded
    private MilestoneBody milestoneBody;

    public Milestone() {
    }

    public Milestone(MilestoneBody milestoneBody) {
        this.milestoneBody = milestoneBody;
    }

    public Milestone(long id, MilestoneBody milestoneBody) {
        super(id);
        this.milestoneBody = milestoneBody;
    }

    public MilestoneBody getMilestoneBody() {
        return milestoneBody;
    }

    public void setMilestoneBody(MilestoneBody milestoneBody) {
        this.milestoneBody = milestoneBody;
    }

    public boolean hasSameBody(MilestoneBody target) {
        return milestoneBody.equals(target);
    }

    @Override
    public String toString() {
        return "Milestone[milestoneBody=" + milestoneBody + "]";
    }
}
