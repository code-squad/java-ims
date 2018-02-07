package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Label extends AbstractEntity {
    private String subject;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_label_to_user"))
    private User writer;

    public Label() {
    }

    public Label(String subject) {
        this(0L, subject);
    }

    public Label(long id, String subject) {
        super(id);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public boolean isWritedBy(User loginUser) {
        return writer.equals(loginUser);
    }

    public void writedBy(User loginUser) {
        this.writer = loginUser;
    }
}
