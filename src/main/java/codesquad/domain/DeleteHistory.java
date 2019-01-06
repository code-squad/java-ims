package codesquad.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DeleteHistory {
    @Id
    @GeneratedValue
    private Long id;

    private String contentType;

    private Long contentId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_deletehistory_to_user"))
    private User deletedBy;

    private LocalDateTime createDate = LocalDateTime.now();

    public DeleteHistory() {
    }

    public DeleteHistory(Long contentId, User deletedBy) {
        this.contentType = "issue";
        this.contentId = contentId;
        this.deletedBy = deletedBy;
    }

    @Override
    public String toString() {
        return "DeleteHistory [id=" + id + ", contentType=" + contentType + ", contentId=" + contentId + ", deletedBy="
                + deletedBy + ", createDate=" + createDate + "]";
    }

}