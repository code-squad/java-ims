package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Where;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Milestone extends AbstractEntity {

    @Size(min = 3, max = 30)
    @Column(nullable = false, length = 30)
    private String subject;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_writer"))
    private User writer;

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL)
    @Where(clause = "delete = false")
    @OrderBy("id ASC")
    @JsonIgnore
    private List<Issue> issues;

    private String startDate;

    private String endDate;

    public Milestone() {
    }

    public Milestone(String subject) {
        this.subject = subject;
    }

    public Milestone(String subject, String startDate, String endDate, User user) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
        this.writer = user;
    }

    public Milestone(long id, String subject, String startDate, String endDate, User writer) {
        super(id);
        this.subject = subject;
        this.writer = writer;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
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

    public void writeBy(User user) {
        this.writer = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Milestone milestone = (Milestone) o;

        if (subject != null ? !subject.equals(milestone.subject) : milestone.subject != null) return false;
        if (writer != null ? !writer.equals(milestone.writer) : milestone.writer != null) return false;
        if (startDate != null ? !startDate.equals(milestone.startDate) : milestone.startDate != null) return false;
        return endDate != null ? endDate.equals(milestone.endDate) : milestone.endDate == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        result = 31 * result + (writer != null ? writer.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Milestone{" +
                "subject='" + subject + '\'' +
                ", writer=" + writer +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                '}';
    }
}



