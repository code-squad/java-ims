package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Assignee {

    @Id
    @GeneratedValue
    Long id;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Assignee{" +
                "userId=" + userId +
                ", issueId=" + issueId +
                '}';
    }
}
