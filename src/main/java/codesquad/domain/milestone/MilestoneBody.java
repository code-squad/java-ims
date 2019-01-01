package codesquad.domain.milestone;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Embeddable
public class MilestoneBody {

    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String subject;

    // todo 추가 제한 사항 필요
    @DateTimeFormat
    private LocalDateTime startDate;

    // todo 추가 제한 사항 필요
    @DateTimeFormat
    private LocalDateTime endDate;

    public MilestoneBody() {
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

    @Override
    public String toString() {
        return "MilestoneBody [subject=" + subject + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }
}
