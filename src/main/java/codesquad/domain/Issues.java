package codesquad.domain;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Issues {

    @OneToMany
    private List<Issue> issues = new ArrayList<>();

    public void addIssue(Issue issue, Milestone milestone) {
        issues.add(issue);
        issue.setMilestone(milestone);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
