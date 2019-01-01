package codesquad.domain.milestone;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Embeddable
public class MilestoneBody {

    @Size(min = 3, max = 30)
    @Column(length = 30, nullable = false)
    private String subject;

    // todo 추가 제한 사항 필요
    @DateTimeFormat
    private LocalDateTime startDate;

    // todo 추가 제한 사항 필요
    @DateTimeFormat
    private LocalDateTime endDate;

    public MilestoneBody() {
    }

    public MilestoneBody(String subject, LocalDateTime startDate, LocalDateTime endDate) {
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

    public String getEndDate() {
//        June 23, 2016
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        return endDate.format(dateTimeFormatter);
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "MilestoneBody [subject=" + subject + ", startDate=" + startDate + ", endDate=" + endDate + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MilestoneBody that = (MilestoneBody) o;
        return Objects.equals(subject, that.subject) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, startDate, endDate);
    }
}
