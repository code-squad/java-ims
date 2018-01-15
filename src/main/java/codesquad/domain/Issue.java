package codesquad.domain;

import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;
	
	private String title;

	@Lob
	private String contents;
	
	public Issue() {
	}
	
	public Issue(String title, String contents, User writer) {
		this.title = title;
		this.contents = contents;
		this.writer = writer;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
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

	public boolean isSameWriter(User loginUser) {
		return this.writer.equals(loginUser);
	}

	public void update(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
	
	@Override
	public String toString() {
		return "Issue [title=" + title + ", contents=" + contents + "]";
	}
}
