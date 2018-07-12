package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {

    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String title;

    @Size(min = 3)
    @Lob
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    public Issue() {}

    public Issue(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writer=" + writer +
                '}';
    }
}
