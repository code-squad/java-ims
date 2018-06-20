package codesquad.domain;

import org.hibernate.annotations.Where;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;

@Embeddable
public class Issues {

    @OneToMany(mappedBy = "milestone")
    @Where(clause = "deleted = false")
    private List<Issue> issues;

    public int getOpenCount() {
        return (int) issues.stream()
                .filter(issue -> !issue.isDeleted())
                .filter(issue -> issue.isOpen())
                .count();
    }

    public int getClosedCount() {
        return (int) issues.stream()
                .filter(issue -> !issue.isDeleted())
                .filter(issue -> !issue.isOpen())
                .count();
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
