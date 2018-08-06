package codesquad.domain;

import org.apache.tomcat.jni.Local;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class Milestone extends AbstractEntity {
    private static final Logger log =  LoggerFactory.getLogger(Milestone.class);

    @Column(length = 50)
    private String subject;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Transient
    private String html_startdate;

    @Transient
    private String html_enddate;

    public Milestone() {
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

    public void setHtml_enddate(String html_enddate) {
        endDate = stringToLocalDateTime(html_enddate);
    }

    public void setHtml_startdate(String html_startdate) {
        startDate = stringToLocalDateTime(html_startdate);
    }

    private LocalDateTime stringToLocalDateTime(String date) {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
    }

    public String getDueDate() {
        return endDate.format(DateTimeFormatter.ofPattern("'Due by' MMM dd, yyyy"));
    }

}
