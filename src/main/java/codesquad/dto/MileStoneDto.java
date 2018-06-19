package codesquad.dto;

import codesquad.domain.MileStone;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MileStoneDto {
    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String subject;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public MileStoneDto(String subject, LocalDateTime startTime, LocalDateTime endTime) {
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public MileStoneDto(long id, String subject, LocalDateTime startTime, LocalDateTime endTime) {
        super();
        this.subject = subject;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public MileStone toMileStone() {
        return new MileStone(this.subject, this.startTime, this.endTime);
    }

    public String getSubject() {
        return subject;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public MileStoneDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public MileStoneDto setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    public MileStoneDto setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
        return this;
    }
}
