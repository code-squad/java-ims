package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Issue extends AbstractEntity {

    @Column(length = 30, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private IssueStatus status = IssueStatus.OPEN;

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

    @Override
    public String toString() {
        return "Issue{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
