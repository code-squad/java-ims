package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Issue extends AbstractEntity {

    @Embedded
    private Content content;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    private boolean deleted = false;

    public Issue() {

    }

    public Issue(Content content, User writer) {
        this.writer = writer;
        this.content = content;
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

    public IssueDto _toIssueDto() {
        return new IssueDto(this.content, this.writer);
    }

    public boolean isOneSelf(User loginUser) {
        return this.writer.equals(loginUser);
    }

    public Issue update(Issue issue) {
        this.content = issue.content;
        return this;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "content=" + content +
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }
}
