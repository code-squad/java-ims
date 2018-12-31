package codesquad.domain;

import codesquad.dto.IssueDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {
    @Size(min = 6, max = 20)
    @Column(length = 20)
    @JsonIgnore
    private String userId;

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String subject;

    @Size(min = 5, max = 100)
    @Column(nullable = false, length = 100)
    private String comment;

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(String userId, String subject, String comment) {
        this.userId = userId;
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(long id, String userId, String subject, String comment) {
        super(id);
        this.userId = userId;
        this.subject = subject;
        this.comment = comment;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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