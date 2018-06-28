package codesquad.domain;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class DeleteHistory {

    @Id
    @GeneratedValue
    private Long id;

    private Long contentId;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_deletehistory_to_user"))
    private User deletedBy;

    private LocalDateTime createTime = LocalDateTime.now();

    public DeleteHistory(){
    }

    public DeleteHistory(long contentId, ContentType contentType, User deletedBy){
        this.contentId = contentId;
        this.contentType = contentType;
        this.deletedBy = deletedBy;
        this.createTime = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeleteHistory that = (DeleteHistory) o;
        return Objects.equals(contentId, that.contentId) &&
                contentType == that.contentType &&
                Objects.equals(deletedBy, that.deletedBy);
    }

    @Override
    public int hashCode() {

        return Objects.hash(contentId, contentType, deletedBy);
    }
}
