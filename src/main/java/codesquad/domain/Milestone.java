package codesquad.domain;

import org.hibernate.annotations.Where;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Milestone extends AbstractEntity {
    @Size(min = 3, max = 50)
    @Column(length = 50, nullable = false)
    private String subject;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Issue> issues = new ArrayList<>();

    public Milestone() {
    }

    public Milestone(long id, String subject, LocalDateTime startDate, LocalDateTime endDate) {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public String getFormattedDueDate() {
        return endDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
    }

    public void addIssue(Issue issue) {
        issues.add(issue);
    }

    public long getOpenIssue() {
        return issues.stream().filter(issue -> !issue.isClosed()).count();
    }

    public long getCloseIssue() {
        return issues.stream().filter(Issue::isClosed).count();
    }

    public long getProgress() {
        if (issues.size() == 0) return 0;
        return 300 * getOpenIssue() / (getCloseIssue() + getOpenIssue());
    }
}