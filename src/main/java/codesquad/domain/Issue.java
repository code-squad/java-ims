package codesquad.domain;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Issue extends AbstractEntity {
    private String subject;
    private String comment;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_user"))
    private User writer;

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this(0L, subject, comment);
    }

    public Issue(long id, String subject, String comment) {
        super(id);
        this.subject = subject;
        this.comment = comment;
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public void writeBy(User loginUser) {
        writer = loginUser;
    }

    public User getWriter() {
        return writer;
    }

    public boolean isWritedBy(User loginUser) {
        return writer.equals(loginUser);
    }

    public void update(IssueDto issueDto) {
        this.subject = issueDto.getSubject();
        this.comment = issueDto.getComment();
    }
}
