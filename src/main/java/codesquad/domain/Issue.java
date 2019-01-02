package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String subject;

    @Lob
    @Size(min = 3)
    @Column(nullable = false)
    private String comment;

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(long id, String subject, String comment) {
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

    public IssueDto _toIssueDto() {
        return new IssueDto(this.subject, this.comment);
    }
}
