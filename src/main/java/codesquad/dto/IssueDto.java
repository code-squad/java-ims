package codesquad.dto;

import codesquad.domain.Issue;

public class IssueDto {
    private String subject;
    private String comment;

    public IssueDto(){
    }

    public IssueDto(String subject, String comment){
        this.subject = subject;
        this.comment = comment;
    }

    public IssueDto(long id, String subject, String comment){
        super();
        this.subject = subject;
        this.comment = comment;
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Issue _toIssue(){
        return new Issue(this.subject, this.comment);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IssueDto other = (IssueDto) obj;
        if (subject == null) {
            if (other.subject != null)
                return false;
        } else if (!subject.equals(other.subject))
            return false;
        if (comment == null) {
            if (other.comment != null)
                return false;
        } else if (!comment.equals(other.comment))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((subject == null) ? 0 : subject.hashCode());
        result = prime * result + ((comment == null) ? 0 : comment.hashCode());

        return result;
    }

    @Override
    public String toString() {
        return "IssueDto{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
