package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity {

    private static final Logger log = getLogger(Issue.class);

    @Embedded
    private IssueBody issueBody;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(IssueBody issueBody) {
        this.issueBody = issueBody;
    }

    public Issue(User loginUser, IssueBody issueBody) {
        this.writer = loginUser;
        this.issueBody = issueBody;

    }

    public IssueBody getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(IssueBody issueBody) {
        this.issueBody = issueBody;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public void writeBy(User loginUser) {
        this.writer = loginUser;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public IssueDto _toIssueDto() {
        return new IssueDto(this.issueBody);
    }

    public void update(User loginUser, IssueBody target) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.issueBody = target;
    }

    public void delete(User loginUser) {
        log.debug("loginUser : {}", loginUser);
        log.debug("writer : {}", writer);
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.deleted = true;
    }
}
