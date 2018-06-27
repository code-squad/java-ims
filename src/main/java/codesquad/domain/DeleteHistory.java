package codesquad.domain;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    private LocalDateTime createTime;

    public DeleteHistory(){
    }

    public DeleteHistory(long contentId, ContentType contentType, User deletedBy){
        this.contentId = contentId;
        this.contentType = contentType;
        this.deletedBy = deletedBy;
        this.createTime = LocalDateTime.now();
    }
}
