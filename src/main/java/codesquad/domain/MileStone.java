package codesquad.domain;

import codesquad.dto.MileStoneDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
public class MileStone extends AbstractEntity {
//    private static final Logger log =  LoggerFactory.getLogger(MileStone.class);
//
//    @Size(min = 3, max = 50)
//    @Column(nullable = false)
//    private String subject;
//
//    private LocalDateTime startDate;
//    private LocalDateTime endDate;
//
//    public MileStone() {}
//
//    public MileStone(@Size(min = 3, max = 50) String subject, LocalDateTime startDate, LocalDateTime endDate) {
//        this.subject = subject;
//        this.startDate = startDate;
//        this.endDate = endDate;
//    }
//
//    public MileStoneDto _toMileStoneDto() {
//        return new MileStoneDto(this.subject, this.startDate, this.endDate);
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
//    @Override
//    public String toString() {
//        return "MileStone{" +
//                "subject='" + subject + '\'' +
//                ", startDate=" + startDate +
//                ", endDate=" + endDate +
//                '}';
//    }
private static final Logger log =  LoggerFactory.getLogger(MileStone.class);

    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String subject;

    private String startDate;
    private String endDate;

    public MileStone() {}

    public MileStone(@Size(min = 3, max = 50) String subject, String startDate, String endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MileStoneDto _toMileStoneDto() {
        return new MileStoneDto(this.subject, this.startDate, this.endDate);
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
    public String toString() {
        return "MileStone{" +
                "subject='" + subject + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
