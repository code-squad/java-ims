package codesquad.domain.milestone;

import codesquad.domain.issue.Issue;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Milestone extends AbstractEntity {

    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String subject;

    private String startDate;

    private String endDate;

    private int open, closed;

    @OneToMany(mappedBy = "milestone")
    @OrderBy("id ASC")
    private List<Issue> issues;

    public Milestone() {
    }

    public Milestone(String subject, String startDate, String endDate) {
        this(0L, subject, startDate, endDate);
    }

    public Milestone(long id, String subject, String startDate, String endDate) {
        super(id);
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }
}
