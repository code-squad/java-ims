package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class Milestone extends AbstractEntity {
    private String subject;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_milestone_to_user"))
    private User writer;

    public Milestone() {
    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this(0L, subject, startDate, endDate);
    }

    public Milestone(Long id, String subject, LocalDateTime startTime, LocalDateTime endTime) {
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

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }
}
