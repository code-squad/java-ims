package codesquad.domain;

import codesquad.domain.issue.ContentType;
import codesquad.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class DeleteHistory {
    @Id
    @GeneratedValue
    private long id;

    @Enumerated(EnumType.STRING)
    private ContentType contentType;

    private Long contentId;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_deletehistory_to_issue"))
    private User deletedBy;

    private LocalDateTime createDate = LocalDateTime.now();

    public DeleteHistory() {}

    public DeleteHistory(ContentType contentType, Long contentId, User deletedBy, LocalDateTime createDate) {
        this.contentType = contentType;
        this.contentId = contentId;
        this.deletedBy = deletedBy;
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "DeleteHistory{" +
                "id=" + id +
                ", contentType=" + contentType +
                ", contentId=" + contentId +
                ", deletedBy=" + deletedBy +
                ", createDate=" + createDate +
                '}';
    }
}
