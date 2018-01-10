package codesquad.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;

import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {
	private String title;

	@Lob
	private String contents;
	
	public Issue() {
	}
	
	public Issue(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "Issue [title=" + title + ", contents=" + contents + "]";
	}

	public String getContents() {
		return contents;
	}
	
	public void setContents(String contents) {
		this.contents = contents;
	}
}
