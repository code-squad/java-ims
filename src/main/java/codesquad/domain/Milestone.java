package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
public class Milestone extends AbstractEntity {
    private String subject;
    private LocalDate startDate;
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_to_user"))
    private User writer;

    public Milestone() {
    }

    public Milestone(String subject, LocalDate startDate, LocalDate endDate) {
        this(0L, subject, startDate, endDate);
    }

    public Milestone(Long id, String subject, LocalDate startTime, LocalDate endTime) {
        super(id);
        this.subject = subject;
        this.startDate = startTime;
        this.endDate = endTime;
    }

    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }

    public String getSubject() {
        return subject;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public User getWriter() {
        return writer;
    }
}
