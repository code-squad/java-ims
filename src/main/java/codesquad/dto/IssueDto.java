package codesquad.dto;

import codesquad.domain.Issue;
import codesquad.domain.User;

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

    public Issue toIssue(User writer) {
        return new Issue(subject, comment, writer);
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
