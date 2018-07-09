package codesquad.domain;

import codesquad.UnAuthorizedException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Issue extends AbstractEntity {
    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    @Lob
    private String comment;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
    private Milestone milestone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
    private User assignee;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_label"))
    private Label label;

    @JsonIgnore
    @Embedded
    private Answers answers = new Answers();

    @OneToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_file"))
    private Attachment file;

    public Issue() {
    }

    public Issue(String subject, String comment) {
        this(0L, subject, comment);
    }

    public Issue(Long id, String subject, String comment) {
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

    public User getWriter() {
        return writer;
    }

    public void writeBy(User loginUser) {
        writer = loginUser;
    }

    public boolean isOwner(User loginUser) {
        return writer.equals(loginUser);
    }

    public void milestoneBy(Milestone milestone) {
        this.milestone = milestone;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public void assignTo(User user) {
        assignee = user;
    }

    public User getAsignee() {
        return assignee;
    }

    public void labelBy(Label label) {
        this.label = label;
    }

    public Label getLabel() {
        return label;
    }

    public Attachment getFile() {
        return file;
    }

    public void update(User loginUser, Issue target) {
        // target의 owner와 비교하는게 아니라 현재 Issue의 owner인지 확인
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }

        subject = target.subject;
        comment = target.comment;
    }

    public void addAnswer(Answer answer) {
        answer.applyToIssue(this);
        answers.add(answer);
    }

    public boolean checkAnswerExist() {
        return !answers.isEmpty();
    }

    public boolean checkAllAnswerWriterIsSameWithWriter() {
        return answers.checkAllWriterSameWith(writer);
    }

    public void checkLoginUser(User loginUser) {
        if (!isOwner(loginUser)) {
            throw new UnAuthorizedException();
        }
    }

    public void attach(Attachment file) {
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(subject, issue.subject) &&
                Objects.equals(comment, issue.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, comment);
    }

    @Override
    public String toString() {
        return "Issue{" +
                "subject='" + subject + '\'' +
                ", comment='" + comment + '\'' +
                ", writer=" + writer +
                ", milestone=" + milestone +
                ", assignee=" + assignee +
                ", label=" + label +
                ", answers=" + answers +
                ", file=" + file +
                '}';
    }
}
