package codesquad.domain;

import codesquad.EntityAlreadyExistsException;
import org.hibernate.annotations.Where;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Issues {

    @OneToMany(mappedBy = "milestone")
    @Where(clause = "deleted = false")
    private List<Issue> issues = new ArrayList<>();

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

    public void addIssue(Issue issue) {
        if (contains(issue)) {
            throw new EntityAlreadyExistsException();
        }
        issues.add(issue);
    }

    public boolean contains(Issue issue) {
        return issues.contains(issue);
    }
}
