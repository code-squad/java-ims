package codesquad.domain;

import codesquad.exception.UnAuthenticationException;
import codesquad.exception.UnAuthorizedException;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity {
    private static final Logger log = getLogger(Issue.class);
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String subject;

    @Size(min = 3)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestones"))
    private Milestone milestone;

    private boolean deleted = false;

    public Issue(){
    }

    public Issue(String subject, String comment, User writer) {
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public String getDate() {
        return getFormattedCreateDate();
    }

    public boolean isLogin(User loginUser) {
        if (Objects.isNull(loginUser)) {
            throw new UnAuthenticationException();
        }
        return true;
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(this.subject, this.comment, this.writer._toUserDto(), this.deleted);
    }

    public IssueDto getIssueDto() {
        return this._toIssueDto();
    }

    public boolean isOwner(User loginUser) {
        isLogin(loginUser);
        if (!writer.equals(loginUser)) {
            throw new UnAuthorizedException();
        }
        return true;
    }

    public Issue modify(IssueDto updateIssue, User loginUser) {
        isOwner(loginUser);
        subject = updateIssue.getSubject();
        comment = updateIssue.getComment();
        return this;
    }

    public Issue delete(User loginUSer) {
        isOwner(loginUSer);
        deleted = true;
        return this;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                ", id=" + getId() +
                '}';
    }
}
