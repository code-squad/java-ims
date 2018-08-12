package codesquad.domain;

import codesquad.dto.IssueDto;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    private String subject;

    private String comment;

    private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_id")
    private List<User> assignees = new ArrayList<>();

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this(null, subject, comment, null);
    }

    public Issue(String subject, String comment, User writer) {
        this(null, subject, comment, writer);
    }

    public Issue(Long id, String subject, String comment, User writer) {
        this.id = id;
        this.subject = subject;
        this.comment = comment;
        this.writer = writer;
    }

    public String getSubject() {
        return subject;
    }

    public String getComment() {
        return comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getWriter() {
        return writer;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void setMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", writer=" + writer +
                ", subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", milestone='" + milestone + '\'' +
                ", assignee='" + assignees + '\'' +
                '}';
    }

    public Issue update(Issue updateIssue) {
        if (!this.writer.equals(updateIssue.writer)) {
            throw new IllegalArgumentException("Cannot match user");
        }
        this.subject = updateIssue.subject;
        this.comment = updateIssue.comment;
        return this;
    }

    public Issue update(IssueDto updateIssueDto, User updateWriter) {
        if (!this.writer.equals(updateWriter)) {
            throw new IllegalArgumentException("Cannot match user");
        }
        this.subject = updateIssueDto.getSubject();
        this.comment = updateIssueDto.getComment();
        return this;
    }

    public void deleted() {
        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public List<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(List<User> assignees) {
        this.assignees = assignees;
    }

    public boolean matchWriter(User writer) {
        return this.writer.equals(writer);
    }

    // TODO setMilestone의 중복임, 제거하기
    public void registerMilestone(Milestone milestone) {
        this.milestone = milestone;
    }

    public void registerAssignee(User user) {
        assignees.add(user);
    }
}
