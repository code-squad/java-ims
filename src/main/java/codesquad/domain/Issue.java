package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Issue extends AbstractEntity {

    @Embedded
    private Contents contents = new Contents();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    public Issue() {
    }

    public Issue(String subject, String comment, User writer) {
        contents.setComment(comment);
        contents.setSubject(subject);
        this.writer = writer;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }
}
