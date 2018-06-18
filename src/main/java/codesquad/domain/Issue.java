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
    private String title;

    @Size(min = 3, max = 1000)
    @Column(nullable = false, length = 1000)
    private String contents;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    private boolean deleted;

    public Issue() {
    }

    public Issue(String title, String contents) {
        this.title = title;
        this.contents = contents;
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

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id='" + getId() + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", writer=" + writer +
                ", deleted=" + deleted +
                '}';
    }

    @Override
    public String generateUri() {
        return String.format("/issues/%d", getId());
    }

    public IssueDto _toDto() {
        return new IssueDto(getId(), getTitle(), getContents());
    }

    public IssueDto update(User loginUser, IssueDto updateIssueDto) {
        if (!writer.equals(loginUser)) {
            throw new UnAuthorizedException();
        }
        title = updateIssueDto.getTitle();
        contents = updateIssueDto.getContents();
        return _toDto();
    }
}
