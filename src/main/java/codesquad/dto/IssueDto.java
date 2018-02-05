package codesquad.dto;

import codesquad.domain.Issue;

import javax.validation.constraints.Size;
import java.util.Objects;

public class IssueDto {
    @Size(min = 3)
    private String subject;

    @Size(min = 3)
    private String comment;

    public IssueDto() {

    }

    public IssueDto(String subject, String content) {
        super();
        this.subject = subject;
        this.comment = content;
    }

    public Issue toIssue(){
        return new Issue(this.subject, this.comment);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IssueDto issueDto = (IssueDto) o;
        return Objects.equals(subject, issueDto.subject) &&
                Objects.equals(comment, issueDto.comment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(subject, comment);
    }
}
