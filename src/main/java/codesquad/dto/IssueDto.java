package codesquad.dto;

import codesquad.domain.Issue;

public class IssueDto {
    private String subject;
    private String comment;

    public IssueDto() {

    }

    public IssueDto(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Issue toIssue() {
        return new Issue(subject, comment);
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
