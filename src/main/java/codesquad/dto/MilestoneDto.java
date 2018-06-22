package codesquad.dto;

import codesquad.domain.Milestone;

public class MilestoneDto {

    private long id;
    private String startDate;
    private String dueDate;
    private String subject;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Milestone _toMilestone() {
        return new Milestone(startDate, dueDate, subject);
    }

    @Override
    public String toString() {
        return "MilestoneDto{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
