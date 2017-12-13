package codesquad.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityListeners({ AuditingEntityListener.class })
public class Reply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_reply_writer"))
	@JsonProperty
	private User replyWriter;
	@Lob
	@Column(nullable = false)
	@JsonProperty
	private String replyComment;
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name="fk_reply_issue"))
	@JsonIgnore
	private Issue issue;
	@CreatedDate
	@Column(nullable = false)
	@JsonProperty
	private LocalDateTime replyRegDate;
	@OneToMany(mappedBy="reply", fetch=FetchType.LAZY)
	private List<ReplyFilePath> file;
	
	public void setFile(List<ReplyFilePath> file) {
		this.file = file;
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
	
	public List<ReplyFilePath> getFile() {
		return file;
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