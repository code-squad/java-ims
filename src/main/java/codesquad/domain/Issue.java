package codesquad.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import codesquad.UnAuthenticationException;
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
	
	@OneToMany(mappedBy ="issue")
	@OrderBy("id DESC")
	@JsonIgnore
	private List<Answer> answers;
	
	private boolean closed = false;

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
	

	public List<Answer> getAnswers() {
		return answers;
	}

	public MileStone getMileStone() {
		return mileStone;
	}

	public User getAssignee() {
		return assignee;
	}

	public Label getLabel() {
		return label;
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
		return this.closed;
	}

	public void update(User loginUser, Issue issue) throws UnAuthenticationException {
		checkOwner(loginUser);
		this.subject = issue.subject;
		this.comment = issue.comment;
	}
	
	public void checkOwner(User loginUser) throws UnAuthenticationException {
		if(!isOwner(loginUser)) {
			throw new UnAuthenticationException("본인의 글만 수정, 삭제 가능");
		}
	}


	public void putInMileStone(User loginUser, MileStone mileStone) throws UnAuthenticationException {
		checkOwner(loginUser);
		this.mileStone = mileStone;
	}

	public void appointAssignee(User loginUser, User assignee) throws UnAuthenticationException {
		checkOwner(loginUser);
		this.assignee = assignee;
	}

	public void addLabel(User loginUser, Label label) throws UnAuthenticationException {
		checkOwner(loginUser);
		this.label = label;
	}
	
	@Override
	public String toString() {
		return "Issue [writer=" + writer + ", subject=" + subject + ", comment=" + comment + "]";
	}

}
