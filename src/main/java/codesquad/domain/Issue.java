package codesquad.domain;

import codesquad.UnAuthenticationException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;

@Entity
public class Issue extends AbstractEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_author"))
    private User author;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;
//
//    @Embedded
//    private Attachments attachments;

    public Issue(String title, String comment) {
        this.title = title;
        this.comment = comment;
    }

    public Issue(String title, String comment, User author) {
        this.title = title;
        this.comment = comment;
        this.author = author;
    }

    public Issue(String title, String comment, User author, User assignee) {
        this.title = title;
        this.comment = comment;
        this.author = author;
        this.assignee = assignee;
    }

    public void registerAssignee(User user) {
        this.assignee = assignee;
    }

    public String getTitle() {
        return title;
    }

    public String getComment() {
        return comment;
    }

    public User getAuthor() {
        return author;
    }


    @Override
    public String toString() {
        return "Issue{" +
                "id=" + getId() + '\'' +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                ", author=" + author +
                '}';
    }

    public void update(User loginUser, IssueDto newIssue) throws UnAuthenticationException {
        if (!author.equals(loginUser))
            throw new UnAuthenticationException("cannot update issue");

        this.title = newIssue.getTitle();
        this.comment = newIssue.getComment();
    }
}
