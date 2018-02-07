package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.LabelDto;
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

    public void update(User loginUser, LabelDto labelDto) {
        if (!isWritedBy(loginUser)) {
            throw new UnAuthorizedException("작성자만 수정할 수 있습니다.");
        }

        this.subject = labelDto.getSubject();
    }
}
