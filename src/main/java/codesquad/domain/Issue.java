package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.Entity;

@Entity
public class Issue extends AbstractEntity {

    private String subject;
    private String comment;

    public Issue(){
    }

    public Issue(String subject, String comment){
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(long id, String subject, String comment){
        super(id);
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

    public IssueDto _toIssueDto(){
        return new IssueDto(this.subject, this.comment);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}