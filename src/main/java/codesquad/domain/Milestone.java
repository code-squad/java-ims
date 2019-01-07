package codesquad.domain;

import org.hibernate.annotations.Where;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Milestone {
    private String startDate;
    private String endDate;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Issue> issues = new ArrayList<>();


    public Milestone(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public long getOpenIssue() {
        return issues.stream().filter(issue -> !issue.isClosed()).count();
    }

    public long getCloseIssue() {
        return issues.stream().filter(Issue::isClosed).count();
    }

    public void addIssue(Issue newIssue) {
        newIssue.toMilestone(this);
        issues.add(newIssue);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
