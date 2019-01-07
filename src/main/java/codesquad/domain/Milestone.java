package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Milestone extends AbstractEntity {
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String subject;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public Milestone() {

    }

    public Milestone(String subject, LocalDateTime startDate, LocalDateTime endDate) {
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

    public String getFormattedDueDate() {
        if (endDate == null) {
            return "";
        }
        return endDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));
    }
}
