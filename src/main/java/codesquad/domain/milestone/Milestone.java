package codesquad.domain.milestone;

import codesquad.domain.user.User;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Milestone extends AbstractEntity {

    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String subject;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    private Boolean deleted = false;

    public Milestone() {
    }

    public Milestone(long id, String subject, String startDate, String endDate, User writer) {
        super(id);
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
        this.writer = writer;
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

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
}
