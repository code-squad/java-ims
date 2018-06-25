package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table
public class Issue extends AbstractEntity {
    private static final Logger log =  LoggerFactory.getLogger(Issue.class);

    @Size(min = 3, max = 50)
    @Column(nullable = false)
    private String subject;

    @Size(min = 3)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private MileStone mileStone;

    public Issue(long id, String subject, String comment, User writer) {
        super(id);
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public Issue(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public Issue() {};

    public void update(User writer, Issue target) throws CannotDeleteException {
        if (!isOwner(writer))
            throw new CannotDeleteException("자신이 쓴 글만 업데이트를 할 수 있습니다.");
        this.subject = target.subject;
        this.comment = target.comment;
        log.info("after update issue : {}", this.toString());
    }

    public MileStone getMileStone() {
        return mileStone;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public IssueDto toIssueDto() {
        return new IssueDto(this.subject, this.comment, this.writer);
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public User getWriter() {
        return writer;
    }

    public Issue writeBy(User loginUser) {
        log.info("writeBy : " + loginUser);
        this.writer = loginUser;
        return this;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }
}
