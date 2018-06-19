package codesquad.dto;

import codesquad.domain.MileStone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MileStoneDto {
    private static final Logger log =  LoggerFactory.getLogger(MileStoneDto.class);

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    public MileStoneDto() {
        log.info("생성자호출 1 : {}", toString());
    }

    public MileStoneDto(String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
        log.info("생성자호출 2 : {}", toString());
    }

    public MileStoneDto(long id, String subject, LocalDateTime startDate, LocalDateTime endDate) {
        super();
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
        log.info("생성자호출 3 : {}", toString());
    }

    public MileStone toMileStone() {
        return new MileStone(this.subject, this.startDate, this.endDate);
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

    public MileStoneDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public MileStoneDto setStartDate(String startDate) {
        this.startDate = LocalDateTime.parse(startDate);
        return this;
    }

    public MileStoneDto setEndDate(String endDate) {
        this.endDate = LocalDateTime.parse(endDate);
        return this;
    }

    @Override
    public String toString() {
        return "MileStoneDto{" +
                "subject='" + subject + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
