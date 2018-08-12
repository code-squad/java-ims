package codesquad.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Embeddable
public class Assignees {

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Assignee> assignees = new ArrayList<>();

    public Assignees addAssignee(Long userId, Long issueId) {
        assignees.add(new Assignee(userId, issueId));
        return this;
    }

    @Override
    public String toString() {
        return "Assignees{" +
                "assignees=" + Arrays.toString(assignees.toArray()) +
                '}';
    }
}
