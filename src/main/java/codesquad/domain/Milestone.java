package codesquad.domain;


import org.hibernate.annotations.Where;
import org.springframework.transaction.annotation.Transactional;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Milestone extends AbstractEntity {

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String subject;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    @Where(clause = "deleted = false")
    @OrderBy("id ASC")
    private List<Issue> issues = new ArrayList<>();

    public Milestone () {

    }

    public Milestone (long id, String subject, Date startDate, Date endDate) {
        super(id);
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Milestone (String subject, Date startDate, Date endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getSubject() {
        return subject;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void addToIssue(Issue issue){
        this.issues.add(issue);
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Milestone milestone = (Milestone) o;
        return Objects.equals(subject, milestone.subject) &&
                Objects.equals(startDate, milestone.startDate) &&
                Objects.equals(endDate, milestone.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), subject, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "id='" + getId() + '\'' +
                "subject='" + subject + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
