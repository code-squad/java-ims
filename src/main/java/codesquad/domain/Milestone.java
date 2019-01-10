package codesquad.domain;

import org.hibernate.annotations.Where;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Entity
public class Milestone extends AbstractEntity {
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
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

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void addIssue(User loginUser, Issue issue) {
        issue.toMilestone(loginUser, this);
        issues.add(issue);
    }

    public int getCountOfClosedIssue() {
        int count = 0;
        for (Issue issue : issues) {
            if(issue.isClosed()) {
                count ++;
            }
        }
        return count;
    }

    public int getCountOfOpenedIssue() {
        return issues.size() - getCountOfClosedIssue();
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

    public String getFormattedDueDate() {
        if (endDate == null) {
            return "";
        }
        return endDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.US));
    }
}
