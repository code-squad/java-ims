package codesquad.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Milestone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String dueDate;

    @Column(nullable = false)
    @Size(min = 2, max = 30)
    private String subject;

    @Embedded
    private Issues issues;

    private boolean deleted;


    public Milestone() {
    }

    public Milestone(String startDate, String dueDate, String subject) {
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.subject = subject;
    }

    private String convertToStringFormat(LocalDateTime dateTime) {
        return new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z").format(dateTime);
    }

    public int getOpenIssueCount() {
        return issues.getOpenCount();
    }

    public int getClosedIssueCount() {
        return issues.getClosedCount();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<Issue> getIssues() {
        return issues.getIssues();
    }

    public void setIssues(List<Issue> issues) {
        this.issues.setIssues(issues);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Milestone)) return false;
        Milestone milestone = (Milestone) o;
        return id == milestone.id &&
                Objects.equals(startDate, milestone.startDate) &&
                Objects.equals(dueDate, milestone.dueDate) &&
                Objects.equals(subject, milestone.subject);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, startDate, dueDate, subject);
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void addIssue(Issue issue) {
        issues.addIssue(issue);
    }
}
