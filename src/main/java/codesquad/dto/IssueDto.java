package codesquad.dto;

import codesquad.domain.Issue;

import javax.validation.constraints.Size;

public class IssueDto {
    @Size(min = 3, max = 50)
    private String subject;

    @Size(min = 3, max = 500)
    private String comment;

    public IssueDto() {
    }

    public IssueDto(@Size(min = 3, max = 50) String subject, @Size(min = 3, max = 500) String content) {
        this.subject = subject;
        this.comment = content;
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

    public Issue _toIssue() {
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
