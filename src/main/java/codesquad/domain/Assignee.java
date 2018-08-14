package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Assignee extends AbstractEntity {

    @Column
    private Long userId;

    @Column
    private Long issueId;

    public Assignee() {
    }

    public Assignee(Long userId, Long issueId) {
        this.userId = userId;
        this.issueId = issueId;
    }

    @Override
    public String toString() {
        return "Assignee{" +
                "userId=" + userId +
                ", issueId=" + issueId +
                '}';
    }
}
