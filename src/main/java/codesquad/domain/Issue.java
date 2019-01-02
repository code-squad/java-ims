package codesquad.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity {

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @Embedded
    private IssueBody issueBody;

    public Issue() {
    }

    public Issue(User writer, IssueBody issueBody) {
        this.writer = writer;
        this.issueBody = issueBody;
    }

    public static Issue ofBody(User loginUser, IssueBody issueBody) {
        return new Issue(loginUser,issueBody);
    }

    public String getUserId() {
        return writer.getName();
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public IssueBody getIssueBody() {
        return issueBody;
    }

    public void setIssueBody(IssueBody issueBody) {
        this.issueBody = issueBody;
    }

    public void setUserId(User writher) {
        this.writer = writher;
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

}