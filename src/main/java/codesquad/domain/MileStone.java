package codesquad.domain;

import codesquad.dto.MileStoneDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class MileStone extends AbstractEntity {
    private static final Logger log =  LoggerFactory.getLogger(MileStone.class);

    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String subject;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @OneToMany(mappedBy = "mileStone")
    private List<Issue> issues;

    public MileStone() {}

    public MileStone(@Size(min = 3, max = 50) String subject, LocalDateTime startDate, LocalDateTime endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    public MileStone(long id, String subject, LocalDateTime startDate, LocalDateTime endDate) {
        super(id);
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public MileStoneDto _toMileStoneDto() {
        return new MileStoneDto(this.subject, this.startDate, this.endDate);
    }

    public List<Issue> getIssues() {
        return issues;
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

    @Override
    public String toString() {
        return "MileStone{" +
                "subject='" + subject + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
