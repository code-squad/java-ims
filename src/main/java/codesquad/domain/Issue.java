package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity{

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String subject;

    @Size(min = 3)
    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_question_writer"))
    private User writer;

    private boolean deleted;

    public Issue() {

    }

    public Issue(User writer, String subject, String comment) {
        this.writer = writer;
        this.subject = subject;
        this.comment = comment;
        this.deleted = false;
    }

    public Issue(String subject, String comment) {
        this.subject = subject;
        this.comment = comment;
        this.deleted = false;
    }

    public IssueDto toDto(){
        return new IssueDto(getSubject(), getComment());
    }

    public void writeBy(User user) {
        this.writer = user;
    }

    public void update(User loginUser, IssueDto issueDto) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }

        this.subject = issueDto.getSubject();
        this.comment = issueDto.getComment();
    }

    public void delete(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
        this.deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public User getWriter() {
        return writer;
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Issue issue = (Issue) o;
        return Objects.equals(getId(), issue.getId()) &&
                Objects.equals(subject, issue.subject) &&
                Objects.equals(comment, issue.comment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), subject, comment);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + getId() + '\'' +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
