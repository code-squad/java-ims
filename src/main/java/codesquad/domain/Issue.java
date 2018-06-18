package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;
import support.domain.UriGeneratable;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class Issue extends AbstractEntity implements UriGeneratable {

    @Size(min = 3, max = 100)
    @Column(nullable = false, length = 100)
    private String subject;

    @Size(min = 3, max = 1000)
    @Column(nullable = false, length = 1000)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    private boolean deleted;

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
        deleted = false;
    }

    public long getId() {
        return super.getId();
    }

    public User getWriter() {
        return writer;
    }

    public Issue writeBy(User loginUser) {
        if (writer == null) {
            writer = loginUser;
        }
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public String getFormattedModifiedDate() {
        return super.getFormattedModifiedDate();
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + getId() + '\'' +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public String generateUri() {
        return String.format("/issues/%d", getId());
    }

    public IssueDto _toDto() {
        return new IssueDto(getId(), getSubject(), getComment());
    }

    public Issue update(User loginUser, IssueDto updateIssueDto) {
        if (!writer.equals(loginUser)) {
            throw new UnAuthorizedException();
        }
        subject = updateIssueDto.getSubject();
        comment = updateIssueDto.getComment();
        return this;
    }
}
