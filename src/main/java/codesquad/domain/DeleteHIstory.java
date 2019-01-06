package codesquad.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DeleteHIstory {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_deletehistory_to_user"))
    private User deleteBy;

    private LocalDateTime createTime = LocalDateTime.now();

    public DeleteHIstory() {
    }

    public DeleteHIstory(ContentType contentType,Long id, User deleteBy) {
        this.contentType = contentType;
        this.id = id;
        this.deleteBy = deleteBy;
    }

    @Override
    public String toString() {
        return "DeleteHIstory{" +
                "id=" + id +
                ", contentType=" + contentType +
                ", deleteBy=" + deleteBy +
                ", createTime=" + createTime +
                '}';
    }
}