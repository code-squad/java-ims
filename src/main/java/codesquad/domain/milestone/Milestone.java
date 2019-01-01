package codesquad.domain.milestone;

import support.domain.AbstractEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class Milestone extends AbstractEntity {

    @Embedded
    private MilestoneBody milestoneBody;
}
