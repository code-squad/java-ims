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

    private boolean deleted = false;

    public Comment() {

    }

    public Comment(String contents) {
        this(0L, null, contents);
    }

    public Comment(User writer, String contents) {
        this(0L, writer, contents);
    }

    public Comment(Long id, User writer, String contents) {
        super(id);
        this.writer = writer;
        this.contents = contents;
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

    public String generatedUri(Long issueId) {
        return "/api/issues/" + issueId + "/comments/" + getId();
    }

    public Comment update(Comment updatedComment) {
        if (!writer.equals(updatedComment.writer)) {
            throw new IllegalStateException("not matched user");
        }
        this.contents = updatedComment.contents;
        return this;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete(User user) {
        if (!this.writer.equals(user)) {
            throw new IllegalArgumentException("not match user");
        }
        deleted = true;
    }
}
