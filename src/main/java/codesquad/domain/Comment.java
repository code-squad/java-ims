package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.dto.CommentDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Comment extends AbstractEntity{

    @Lob
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_comment_issue"))
    private Issue issue;

    private boolean deleted = false;

    public Comment() {
    }

    public Comment(String comment) {
        super(0L);
        this.comment = comment;
    }

    public CommentDto toCommentDto(){
        return new CommentDto(comment);
    }

    public Comment update(User loginUser, Comment target) {
        if (!isOwner(loginUser)){
            throw new UnAuthorizedException();
        }
        this.comment = target.comment;
        return this;
    }

    private boolean isOwner(User loginUser) {
    return writer.equals(loginUser);
    }

    public DeleteHistory delete(User loginUser){
        if(!isOwner(loginUser)){
            throw new CannotDeleteException("자신의 글만 지울 수 있습니다.");
        }
        deleted = true;
        return new DeleteHistory(getId(), ContentType.COMMENT, loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    public String getComment() {
        return comment;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public void writeBy(User loginUser){
        this.writer = loginUser;
    }

    public User getWriter() {
        return writer;
    }

    public Issue getIssue() {
        return issue;
    }
}
