package codesquad.domain;

import javax.persistence.Embeddable;

@Embeddable
public class Assignee {
    private Long userId;
    private Long issueId;

    public Assignee(User user, Issue issue) {
        userId = user.getId();
        issueId = issue.getId();
    }

    @Override
    public String toString() {
        return "Assignee{" +
                "userId=" + userId +
                ", issueId=" + issueId +
                '}';
    }
}
