package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {

    @Size(min = 5, max = 200)
    @Column(nullable = false)
    private String subject;

    @Size(min = 5, max = 1000)
    @Column(nullable = false)
    private String comment;

    /*@ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;*/

    public Issue() {

    }

    public Issue(String subject, String comment) {
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
