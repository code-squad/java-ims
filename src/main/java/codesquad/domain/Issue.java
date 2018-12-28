package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {

    @Size(min = 3, max = 50)
    @Column(nullable = false, length = 50)
    private String subject;

    @Size(min = 3, max = 500)
    @Column(nullable = false, length = 500)
    private String comment;

    public Issue() {
    }

    public Issue(@Size(min = 3, max = 50) String subject, @Size(min = 3, max = 500) String content) {
        this.subject = subject;
        this.comment = content;
    }

    public Issue(long id, @Size(min = 3, max = 50) String subject, @Size(min = 3, max = 500) String content) {
        super(id);
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

//    ApiIssueController에서 사용할 것이지만 일단은 만들어 보았다.
//    public IssueDto _toIssueDto() {
//        return new IssueDto(this.subject, this.comment);
//    }
}
