package codesquad.domain;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.List;

@Embeddable
public class Issues {
    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    private List<Issue> issues;

    public Issues() {
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void add(Issue issue) {
        issues.add(issue);
    }

    public boolean isEmpty() {
        return issues.isEmpty();
    }
}
