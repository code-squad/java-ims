package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.security.sasl.AuthenticationException;
import javax.validation.constraints.Size;


import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
	private User assignee;
	
	@Size(min = 2, max = 15)
	@Column(nullable = false)
	private String subject;

	@Lob
	@Column(nullable = false)
	private String comment;
	
	@ManyToOne
	@JoinColumn(nullable=true, foreignKey = @ForeignKey(name ="fk_issue_mileStone"))
	private MileStone mileStone;

	@ManyToOne
	@JoinColumn(nullable=true, foreignKey = @ForeignKey(name="fk_issue_label"))
	private Label label;
	
	private boolean closed = true;

	public Issue() {
	}

	public Issue(String subject, String comment) {
		this.subject = subject;
		this.comment = comment;
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

	public void writeBy(User loginUser) {
		writer = loginUser;
	}

	public IssueDto toIssueDto() {
		return new IssueDto(getId(), this.subject, this.comment);
	}

	public boolean isOwner(User loginUser) {
		return writer.equals(loginUser);
	}

	public boolean isClosed() {
		return closed;
	}

	public void update(User loginUser, Issue issue) throws AuthenticationException {
		checkOwner(loginUser);
		this.subject = issue.subject;
		this.comment = issue.comment;
	}
	
	public void checkOwner(User loginUser) throws AuthenticationException {
		if(!isOwner(loginUser)) {
			throw new AuthenticationException("본인의 글만 수정, 삭제 가능");
		}
	}

	@Override
	public String toString() {
		return "Issue [writer=" + writer + ", subject=" + subject + ", comment=" + comment + "]";
	}

	public void setMileStone(User loginUser, MileStone mileStone) throws AuthenticationException {
		checkOwner(loginUser);
		this.mileStone = mileStone;
		this.closed = false;
	}

	public void setAssignee(User loginUser, User assignee) throws AuthenticationException {
		checkOwner(loginUser);
		this.assignee = assignee;
	}

	public void setLabel(User loginUser, Label label) throws AuthenticationException {
		checkOwner(loginUser);
		this.label = label;
	}

}
