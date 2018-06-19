package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.EnumType.STRING;

@Entity
public class DeleteHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(STRING)
    private ContentType contentType;

    private long contentId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_deletehistory_to_user"))
    private User deletedBy;

    private LocalDateTime deleteTime = LocalDateTime.now();

    public DeleteHistory() {
    }

    public DeleteHistory(ContentType contentType, long contentId, User deletedBy) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.deletedBy = deletedBy;
    }

    public long getId() {
        return id;
    }

    public String getContentType() {
        return contentType.name();
    }

    public long getContentId() {
        return contentId;
    }

    public String getDeletedBy() {
        return deletedBy.getName();
    }

    public String getDeleteTime() {
        return deleteTime.toString();
    }

    public static DeleteHistory convert(ContentType contentType, User deletedBy, AbstractEntity entity) {
        return new DeleteHistory(contentType, entity.getId(), deletedBy);
    }

    @Override
    public String toString() {
        return "DeleteHistory{" +
                "id=" + id +
                ", contentType=" + contentType +
                ", contentId=" + contentId +
                ", deletedBy=" + deletedBy +
                ", deleteTime=" + deleteTime +
                '}';
    }
}
