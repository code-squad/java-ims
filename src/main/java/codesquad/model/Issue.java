package codesquad.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityListeners({ AuditingEntityListener.class })
public class Issue {
	private final static AtomicLong autoIncrementId = new AtomicLong(4);
	@Id
	private long id;
	@Column(nullable = false)
	private String subject;
	@Lob
	@Column(nullable = false)
	private String comment;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"), nullable = false)
	private User writer;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
	private User assignee;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
	private Milestone milestone;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_label"))
	private Label label;
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime regDate;
	@LastModifiedDate
	private LocalDateTime modifiedDate;
	@Transient
	private User loginUser;
	@OneToMany(mappedBy="issue", fetch=FetchType.LAZY)
	@JsonProperty
	private List<Reply> reply;

	public Issue() {
		id = autoIncrementId.incrementAndGet();
	}
	
	public void setReply(List<Reply> reply) {
		this.reply = reply;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}
	
	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}
	
	public List<Reply> getReply() {
		return reply;
	}
	
	public boolean getIsOwner() {
		return writer.equals(loginUser);
	}

	public void setRegDate(LocalDateTime regDate) {
		this.regDate = regDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}

	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public String getSubject() {
		return subject;
	}

	public String getComment() {
		return comment;
	}

	public String getDate() {
		if (modifiedDate == null) {
			return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return modifiedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public long getId() {
		return id;
	}

	public User getWriter() {
		return writer;
	}

	public void updateSubject(String subject) {
		this.subject = subject;
	}

	public void updateComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Issue other = (Issue) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "subject : " + subject + " comment : " + comment + " date : " + regDate;
	}
}