package codesquad.domain;

import codesquad.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonProperty;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Answer extends AbstractEntity {
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_issue"))
    @JsonProperty
    private Issue issue;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_to_user"))
    private User user;

    @Lob
    @JsonProperty
    private String comment;

    @Column(nullable = false)
    @JsonProperty
    private boolean deleted = false;

    public Answer() {
    }

    public Answer(User user, String comment) {
        this.user = user;
        this.comment = comment;
    }

    public String getUserId() {
        return user.getUserId();
    }

    public String getComment() {
        return comment;
    }

    public void toIssue(Issue issue) {
        this.issue = issue;
    }

    public boolean isOwner(User loginUser) {
        return user.matchUser(loginUser);
    }

    public Answer update(User loginUser, String comment) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException("작성자가 아닙니다.");
        }
        this.comment = comment;
        return this;
    }

    public DeleteHistory deleted(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.deleted = true;
        return new DeleteHistory(ContentType.ANSWER, getId(), loginUser);
    }

    public boolean isDeleted() {
        return deleted;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "issue=" + issue +
                ", user=" + user +
                ", comment='" + comment + '\'' +
                ", deleted=" + deleted +
                '}';
    }
}
