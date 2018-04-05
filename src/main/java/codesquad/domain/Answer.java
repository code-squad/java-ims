package codesquad.domain;

import java.net.URI;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import support.domain.AbstractEntity;

@Entity
public class Answer extends AbstractEntity {
	
	@Column(nullable = false)
	String contents;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_answer_writer"))
	private User writer;
	
	@Column
	boolean deleted = false;
	
	public Answer() {
	}

	public Answer(String contents) {
		this(0L, contents);
	}

	public Answer(long id, String contents) {
		super(id);
		this.contents = contents;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public URI createURI() {
		return URI.create("/api/answers/"+getId());
	}

	public void update(String contents, User loginUser) {
		if(!writer.equals(loginUser))
			throw new IllegalStateException("자신의 답변만 수정할 수 있습니다");
		this.contents = contents;
	}

	public void delete(User loginUser) {
		if(!writer.equals(loginUser))
			throw new IllegalStateException("자신의 답변만 수정할 수 있습니다");
		deleted = true;
	}
	
	
	
	
	
	
}
