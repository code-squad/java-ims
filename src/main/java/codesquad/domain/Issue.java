package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity {

    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 25)
    private String subject;

    @Size(min = 3)
    @Column(nullable = false, length = 25)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    public Issue() {}

    public Issue(String subject, String comment) {
        super();
        this.subject = subject;
        this.comment = comment;
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public User getWriter() {
        return writer;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                '}';
    }
}
