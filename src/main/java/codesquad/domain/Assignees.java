package codesquad.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Embeddable
public class Assignees {
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "ISSUE_USER",
            joinColumns = @JoinColumn(name = "ISSUE_ID"),
            inverseJoinColumns = @JoinColumn(name = "USER_ID"))
    private List<User> assignees = new ArrayList<>();

    public Assignees addAssignee(User user) {
        assignees.add(user);
        return this;
    }

    @Override
    public String toString() {
        return "Assignees{" +
                "assignees=" + Arrays.toString(assignees.toArray()) +
                '}';
    }
}
