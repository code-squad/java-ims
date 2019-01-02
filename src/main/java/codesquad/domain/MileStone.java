package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class MileStone extends AbstractEntity {
    @Size(min = 1, max = 20)
    @Column(nullable = false, length = 20)
    private String subject;

    @Column(nullable = false)
    private String startDate;

    @Column(nullable = false)
    private String endDate;

    public MileStone() {

    }

    public MileStone(String subject, String startDate, String endDate) {
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
