package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
public class Issue extends AbstractEntity {

    @Column(length = 30, nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    private boolean closed;

    private boolean deleted;

    public Issue() {
    }

    public Issue(String title, String content) {
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

    public String getClosed() {
        if (deleted || closed) {
            return "Closed";
        }
        return "#" + super.getId() + " Open";
    }

    @Override
    public String toString() {
        return "Issue{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
