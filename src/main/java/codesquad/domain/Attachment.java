package codesquad.domain;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import support.domain.AbstractEntity;

@Entity
public class Attachment extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_issue"))
	private Issue issue;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_attachment_writer"))
	@JsonProperty
	private User writer;

	@Column(nullable = false)
	@JsonProperty
	private String originalFileName;

	@Column(nullable = false)
	private String saveFileName;
	
	@Column(nullable = false)
	private String path;

	public Attachment() {
	}
	
	public Attachment(User loginUser, Issue issue, String originalName, String uploadPath) throws UnsupportedEncodingException {
		this.writer = loginUser;
		this.issue = issue;
		this.originalFileName = originalName;
		this.saveFileName = UUID.randomUUID()+"_"+originalName;
		this.path = uploadPath;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public String getSaveFileName() {
		return saveFileName;
	}

	public User getWriter() {
		return writer;
	}

	public Issue getIssue() {
		return issue;
	}

	public String getPath() {
		return path;
	}
}
