package codesquad.domain;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.web.multipart.MultipartFile;

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
	private String path = "c:/codeSquad/java-ims/src/main/resources/upload/";

	public Attachment() {
	}
	
	public Attachment(User loginUser, Issue issue, MultipartFile file) throws UnsupportedEncodingException {
		if(file.isEmpty()) {
			throw new NullPointerException();
		}
		this.writer = loginUser;
		this.issue = issue;
		this.originalFileName = file.getOriginalFilename();
		this.saveFileName = UUID.randomUUID()+"_"+file.getOriginalFilename();
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
