package codesquad.dto;

import codesquad.domain.MileStone;

import javax.persistence.Column;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class MileStoneDto {
//    @Size(min = 3, max = 50)
//    @Column(nullable = false)
//    private String subject;
//
//    @Column(nullable = false)
//    private LocalDateTime startDate;
//
//    @Column(nullable = false)
//    private LocalDateTime endDate;
//
//    public MileStoneDto() {}
//
//    public MileStoneDto(String subject, LocalDateTime startDate, LocalDateTime endDate) {
//        this.subject = subject;
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }
//
//    public MileStoneDto(long id, String subject, LocalDateTime startDate, LocalDateTime endDate) {
//        super();
//        this.subject = subject;
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }
//
//    public MileStone toMileStone() {
//        return new MileStone(this.subject, this.startDate, this.endDate);
//    }
//
//    public String getSubject() {
//        return subject;
//    }
//
//    public LocalDateTime getStartDate() {
//        return startDate;
//    }
//
//    public LocalDateTime getEndDate() {
//        return endDate;
//    }
//
//    public MileStoneDto setSubject(String subject) {
//        this.subject = subject;
//        return this;
//    }
//
//    public MileStoneDto setStartDate(LocalDateTime startDate) {
//        this.startDate = startDate;
//        return this;
//    }
//
//    public MileStoneDto setEndDate(LocalDateTime endDate) {
//        this.endDate = endDate;
//        return this;
//    }
@Size(min = 3, max = 50)
@Column(nullable = false)
private String subject;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    public MileStoneDto() {}

    public MileStoneDto(String subject, String startDate, String endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MileStoneDto(long id, String subject, String startDate, String endDate) {
        super();
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MileStone toMileStone() {
        return new MileStone(this.subject, this.startDate, this.endDate);
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

    public MileStoneDto setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public MileStoneDto setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public MileStoneDto setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }
}
