package codesquad.domain;

import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {
    private static final Logger log =  LoggerFactory.getLogger(Issue.class);

    @Size(min = 3, max = 50)
    @Column
    private String subject;

    @Size(min = 3)
    @Lob
    private String comment;

    public Issue(long id, String subject, String comment) {
        super(id);
        this.subject = subject;
        this.comment = comment;
    }

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
    }

    public Issue() {};

    public IssueDto toIssueDto() {
        return new IssueDto(this.subject, this.comment);
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }
}
