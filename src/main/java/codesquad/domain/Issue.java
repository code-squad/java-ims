package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Issue extends AbstractEntity {
    private String subject;
    private String comment;
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_user_writer"))
    private User writer;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_milestone"))
    private Milestone milestone;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_user_assignee"))
    private User assignee;

    @OneToMany
    @JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_to_attachment"))
    private List<Attachment> attachments;

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
        this.writer = loginUser;
    }

    public User getWriter() {
        return writer;
    }

    public Milestone getMilestone() {
        return milestone;
    }

    public boolean isWriteBy(User loginUser) {
        return writer.equals(loginUser);
    }

    public void update(User loginUser, IssueDto issueDto) {
        if (!isWriteBy(loginUser)) {
            throw new UnAuthorizedException("작성자만 수정 또는 삭제할 수 있습니다.");
        }
        this.subject = issueDto.getSubject();
        this.comment = issueDto.getComment();
    }

    public void setMilestone(User loginUser, Milestone milestone) {
        if (!isWriteBy(loginUser)) {
            throw new UnAuthorizedException("작성자만 수정할 수 있습니다.");
        }
        this.milestone = milestone;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User loginUser, User assignee) {
        if (!isWriteBy(loginUser)) {
            throw new UnAuthorizedException("작성자만 수정할 수 있습니다.");
        }
        this.assignee = assignee;
    }

    public void addAttachment(User loginUser, Attachment attachment) {
        if (!isWriteBy(loginUser)) {
            throw new UnAuthorizedException("작성자만 파일을 첨부할 수 있습니다.");
        }

        if (this.attachments == null) {
            this.attachments = new ArrayList<>();
        }

        this.attachments.add(attachment);
    }
}
