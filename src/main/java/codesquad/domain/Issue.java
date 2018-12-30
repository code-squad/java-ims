package codesquad.domain;

import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity {
    @Size(min = 3, max = 100)
    @Column(length = 100, nullable = false)
    private String subject;

    @Size(min = 3)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;


    public void writtenBy(User loginUser) {
        if (isLogin(loginUser)) {
            this.writer = loginUser;
        }
    }

    private boolean isLogin(User loginUser) {
        if (Objects.isNull(loginUser)) {
            return false;
        }
        return true;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
}
