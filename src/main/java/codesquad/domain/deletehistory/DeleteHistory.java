package codesquad.domain.deletehistory;

import codesquad.domain.user.User;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class DeleteHistory extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private long contentId; //지우는 친구의 id

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_deleteHistory_to_user"))
    private User deletedBy;

    public DeleteHistory() {
    }

    public DeleteHistory(ContentType contentType, User deletedBy) {
        this.contentType = contentType;
        this.deletedBy = deletedBy;
    }

    public DeleteHistory(ContentType contentType, long contentId, User deletedBy) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.deletedBy = deletedBy;
    }

    @Override
    public String toString() {
        return "DeleteHistory{" +
                "contentType=" + contentType +
                ", deletedBy=" + deletedBy +
                '}';
    }
}
