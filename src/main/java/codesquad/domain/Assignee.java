package codesquad.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Assignee {
    private Long userId;
    private Long issueId;

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
