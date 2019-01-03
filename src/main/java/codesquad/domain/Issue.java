package codesquad.domain;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity{

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @Embedded
    private IssueBody issueBody;

    private boolean deleted = false;

    public Issue() {
    }

    public Issue(User writer, IssueBody issueBody) {
        this.writer = writer;
        this.issueBody = issueBody;
    }

    public static Issue ofBody(User loginUser, IssueBody issueBody) {
        return new Issue(loginUser,issueBody);
    }

    public Issue update(User loginUser, IssueBody issueBody) {
        if (!writer.matchUser(loginUser)) {
            throw new UnAuthorizedException("작성자가 아닙니다.");
        }
        this.issueBody.update(issueBody);
        return this;
    }

    public String getWriter() {
        return writer.getName();
    }

    public void setUserId(User writer) {
        this.writer = writer;
    }

    public String getSubject() {
        return issueBody.getSubject();
    }

    public void setSubject(String subject) {
        this.issueBody.setSubject(subject);
    }

    public String getComment() {
        return this.issueBody.getComment();
    }

    public void setComment(String comment) {
        this.issueBody.setComment(comment);
    }

    public Issue deleted(User loginUser) {
        if (!writer.matchUser(loginUser)) {
            throw new UnAuthorizedException("작성자가 아닙니다.");
        }
        deleted = true;
        return this;
    }

    public boolean isDeleted() {
        return !deleted;
    }
}