package codesquad.domain;

import org.hibernate.annotations.Where;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Milestone extends AbstractEntity {
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column
    private String subject;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Issue> issues = new ArrayList<>();

    public Milestone() {
    }

    public String getFormattedStartDate() {
        return getFormattedDate(startDate, "yyyy.MM.dd HH:mm:ss");
    }

    public String getFormattedEneDate() {
        // Todo Due by June 23, 2016 이런형식으로 바꿔보자.
        return getFormattedDate(endDate, "yyyy.MM.dd HH:mm:ss");
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

    public long getOpenIssue() {
        return issues.stream().filter(issue -> !issue.isClosed()).count();
    }

    public long getCloseIssue() {
        return issues.stream().filter(Issue::isClosed).count();
    }

    public long getProgressRate() {
        if (issues.size() == 0) {
            return 0;
        }
        return 300 * getOpenIssue() / (getCloseIssue() + getOpenIssue());
    }


    public void addIssue(Issue newIssue) {
        newIssue.toMilestone(this);
        issues.add(newIssue);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }
}
