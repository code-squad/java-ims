package codesquad.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;

import codesquad.UnAuthenticationException;
import codesquad.dto.IssueDto;
import codesquad.web.IssueController;

@Entity
public class Issue {
	private static final Logger log = LoggerFactory.getLogger(IssueController.class);
	
	@Id
	@GeneratedValue
	private long id;

	@JsonProperty
	@Size(min = 3, max = 100)
	@Column(unique = false, nullable = false, length = 100)
	private String subject;

	@JsonProperty
	@Size(min = 3, max = 300)
	@Column(nullable = false, length = 300)
	private String comment;
	
	@JsonProperty
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "issue_writer"))
	private User writer;
	
	@JsonProperty
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "issue_milestone"))
	private Milestone milestone;
	

	@JsonProperty
	@OneToMany
	@JoinColumn(foreignKey = @ForeignKey(name = "issue_manager"))
	private List<User> manager;
	
	@JsonProperty
	@OneToMany
	@JoinColumn(foreignKey = @ForeignKey(name = "issue_answer"))
	private List<Answer> comments;
	
	@JsonProperty
	@OneToMany
	@JoinColumn(foreignKey = @ForeignKey(name = "issue_attchment"))
	private List<Attachment> files;
	
	@JsonProperty
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "issue_label"))
	private Label label;

	private boolean deleted = false;
	
	public Issue() {
	}

	public Issue(String subject, String comment) {
		this.subject = subject;
		this.comment = comment;
	}
	
	public void managedBy(User manager) {
		if (this.isManager(manager)) {
			return;
		}
		this.manager.add(manager);
	}
	
	

	public Issue writeBy(User loginUser) {
		this.writer = loginUser;
		return this;
	}
	
	public void registerMilestone(Milestone milestone) {
		this.milestone = milestone;
	}
	
	public Answer addCommentsThatRegisteredMilestone(User loginUser) {
		log.debug("register milestone and add comments method !");
		String comment = loginUser.getUserId() + " changed milestone to [ " + this.milestone.getSubject() + " ] on ";
		return addComment(loginUser, comment);
	}
	
	public Answer addComment(User loginUser, String comment) {
		Answer newAnswer = new Answer(comment);
		newAnswer.writeBy(loginUser);
		this.comments.add(newAnswer);
		return newAnswer;
	}
	
	public Attachment addAttachment(Attachment file) {
		this.files.add(file);
		return file;
	}
	
	public Issue update(User loginUser, String newComment) throws UnAuthenticationException {
		if (!this.isOwner(loginUser)) {
			throw new UnAuthenticationException();
		}
		this.comment = newComment;
		return this;
	}
	
	public void updateLabel(User loginUser, Label label) throws UnAuthenticationException {
		if (this.isManager(loginUser) || this.isOwner(loginUser)) {
			this.label = label;
			return;
		}
		throw new UnAuthenticationException();
	}
	
	public Answer updateLabelThenMakeComment(User loginUser) {
		String comment = loginUser.getUserId() + " add label to [ " + this.label.getSubject() + " ] on ";
		Answer newAnswer = new Answer(comment);
		newAnswer.writeBy(loginUser);
		this.comments.add(newAnswer);
		
		return newAnswer;
	}
	
	public void delete(User loginUser) throws UnAuthenticationException {
		if (!this.isOwner(loginUser)) {
			throw new UnAuthenticationException();
		}
		this.deleted = true;
	}
	
	public boolean isOwner(User loginUser) {
		return this.writer.equals(loginUser);
	}
	
	public boolean isManager(User loginUser) {
		return this.manager.contains(loginUser);
	}
	
	public boolean isDeleted() {
		return deleted;
	}
	
	public IssueDto _toIssueDto() {
		return new IssueDto(this.subject, this.comment, this.writer);
	}

	// === getter setter methods (behind) ===
	public long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public List<Attachment> getFiles() {
		return files;
	}

	public String getComment() {
		return comment;
	}
	
	public Integer getCommentsSize() {
		return this.comments.size();
	}
	
	public User getWriter() {
		return writer;
	}
	
	public List<Answer> getComments() {
		return comments;
	}

	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public List<User> getManager() {
		return manager;
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
}
