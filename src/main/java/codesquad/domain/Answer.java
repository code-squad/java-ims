package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {

    @Column(nullable = false)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_issue"))
    private Issue issue;

    public Answer() {

    }

    public Answer(String comment, User writer) {
        this.comment = comment;
        this.writer = writer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Answer applyWriter(User loginUser) {
        this.writer = loginUser;
        return this;
    }
}
