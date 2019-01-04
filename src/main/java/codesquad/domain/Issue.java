package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.slf4j.Logger;
import support.domain.AbstractEntity;

import javax.persistence.*;

import static org.slf4j.LoggerFactory.getLogger;

@Entity
public class Issue extends AbstractEntity {
    private static final Logger logger = getLogger(Issue.class);

    @Embedded
    private Contents contents = new Contents();

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    public Issue() {
    }

    public Issue(String subject, String comment, User writer) {
        contents.setComment(comment);
        contents.setSubject(subject);
        this.writer = writer;
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

    public boolean isOwner(User loginUser) {
        logger.debug("같냐l : " + loginUser.toString());
        logger.debug("같냐w : " + writer.toString());
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
}
