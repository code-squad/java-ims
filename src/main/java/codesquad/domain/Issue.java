package codesquad.domain;

import codesquad.UnAuthorizedException;
import support.domain.AbstractEntity;
import support.domain.UriGeneratable;

import javax.persistence.*;

@Entity
public class Issue extends AbstractEntity implements UriGeneratable {
    private static final String ROOT_PATH = "/issues/";

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey)
    private User writer;

    @Column(length = 30, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.OPEN;

    @ManyToOne
    private Milestone milestone;

    private boolean deleted;

    public Issue() {
    }

    public Issue(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Issue(long id, String title, String content) {
        super(id);
        this.title = title;
        this.content = content;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return "#" + super.getId()
                + " "
                + status.toString();
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public String generateUri() {
        return ROOT_PATH + getId();
    }

    public Issue update(User loginUser, Issue issue) {
        if (!writer.equals(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.content = issue.content;
        return this;
    }

    public void delete(User loginUser) {
        if (!loginUser.equals(writer)) {
            throw new UnAuthorizedException();
        }
        deleted = true;
    }

    public boolean isOpen() {
        return status.isOpen();
    }
}
