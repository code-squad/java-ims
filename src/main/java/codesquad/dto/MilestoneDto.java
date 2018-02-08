package codesquad.dto;

import codesquad.domain.Milestone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MilestoneDto {
    private static final Logger log = LoggerFactory.getLogger(MilestoneDto.class);

    @Size(min = 3, max = 20)
    private String subject;

    private String startDate;

    private String endDate;

    public MilestoneDto() {

    }

    public MilestoneDto(String subject, String startDate, String endDate) throws Exception {
        super();
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date dateParser(String stringDate) throws Exception {
        DateFormat format = new SimpleDateFormat("YYYY-MM-DD'T'HH:MM", Locale.getDefault());
        log.debug("it's date: {}", format.parse(stringDate));
        return format.parse(stringDate);
    }

    public Milestone toMilestone() throws Exception {
        return new Milestone(this.subject, dateParser(this.startDate), dateParser(this.endDate));
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getSubject() {
        return subject;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MilestoneDto that = (MilestoneDto) o;
        return Objects.equals(subject, that.subject) &&
                Objects.equals(startDate, that.startDate) &&
                Objects.equals(endDate, that.endDate);
    }

    @Override
    public int hashCode() {

        return Objects.hash(subject, startDate, endDate);
    }

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "subject='" + subject + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
