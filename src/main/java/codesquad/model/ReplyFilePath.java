package codesquad.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ReplyFilePath {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_file_reply"))
	private Reply reply;
	
	@Column(nullable=false)
	private String filePath;
	
	public void setReply(Reply reply) {
		this.reply = reply;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public long getId() {
		return id;
	}

	public Reply getReply() {
		return reply;
	}

	public String getFilePath() {
		return filePath;
	}
	
}
