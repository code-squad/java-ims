package codesquad.domain;

import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Issues {

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.PERSIST)
    @Where(clause = "deleted = false")
    private List<Issue> issues = new ArrayList<>();

    public Issues add(Issue issue) {
        issues.add(issue);
        return this;
    }

    public int getOpenCount() {
        return (int) issues.stream().filter(issue -> !issue.isClosed()).count();
    }

    public int getCloseCount() {
        return (int) issues.stream().filter(Issue::isClosed).count();
    }
}
