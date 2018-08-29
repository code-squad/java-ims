package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends AbstractEntity {

    @ManyToOne
    private User writer;

    private String contents;

    private Long issueId;


    public Comment(User writer, String contents) {
        this.writer = writer;
        this.contents = contents;
    }

    public Comment(String contents) {
        this(null, contents);
    }

    public Comment() {
        this(null, null);
    }

    public User getWriter() {
        return writer;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Long getIssueId() {
        return issueId;
    }

    public void setIssueId(Long issueId) {
        this.issueId = issueId;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "contents='" + contents + '\'' +
                '}';
    }

    public Comment writtenby(User writer) {
        this.writer = writer;
        return this;
    }
}
