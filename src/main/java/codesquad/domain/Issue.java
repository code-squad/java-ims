package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity {
    private static final Logger logger = getLogger(Issue.class);

    @Embedded
    private Contents contents = new Contents();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    private Boolean deleted = false;

    public Issue() {
    }

    public Issue(String subject, String comment, User writer) {
        contents.setComment(comment);
        contents.setSubject(subject);
        this.writer = writer;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public Issue update(User loginUser, Contents updateIssueContents) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.contents.setSubject(updateIssueContents.getSubject());
        this.contents.setComment(updateIssueContents.getComment());
        return this;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public List<DeleteHistory> delete(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException("로그인 유저가 이슈 작성자와 달라 삭제할 수 없습니다");
        }

        List<DeleteHistory> histories = new ArrayList<>();

        this.deleted = true;

        histories.add(new DeleteHistory(ContentType.ISSUE, getId(), writer, LocalDateTime.now()));
        return histories;
    }
}
