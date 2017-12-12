package codesquad.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners({ AuditingEntityListener.class })
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_reply_writer"))
	private User replyWriter;
	@Lob
	@Column(nullable = false)
	private String replyComment;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name="fk_reply_issue"))
	private Issue issue;
	@CreatedDate
	@Column(nullable = false)
	private LocalDateTime replyRegDate;
	@Column(nullable = false)
	private boolean isFile = false;
	
	public void setIsFile(boolean isFile) {
		this.isFile = isFile;
	}

	public void setWriter(User replyWriter) {
		this.replyWriter = replyWriter;
	}

	public void setReplyComment(String replyComment) {
		this.replyComment = replyComment;
	}
	
	public void setIssue(Issue issue) {
		this.issue = issue;
	}
	
	public void setReplyRegDate(LocalDateTime replyRegDate) {
		this.replyRegDate = replyRegDate;
	}
	
	public boolean getIsFile() {
		return isFile;
	}

	public String getReplyComment() {
		return replyComment;
	}

	public User getReplyWriter() {
		return replyWriter;
	}

	public long getId() {
		return id;
	}
	
	public Issue getIssue() {
		return issue;
	}
	
	public String getReplyRegDate() {
		return replyRegDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	}
}