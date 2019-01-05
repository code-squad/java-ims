package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class DeleteHistory extends AbstractEntity {

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

//    private Long contentId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_deleteHistory_to_user"))
    private User deletedBy;

    public DeleteHistory() {
    }

    public DeleteHistory(ContentType contentType, User deletedBy) {
        this.contentType = contentType;
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
