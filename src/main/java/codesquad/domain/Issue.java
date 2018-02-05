package codesquad.domain;

import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Issue extends AbstractEntity {
	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String contents;

	public Issue() {
	}

	public Issue(String title, String contents) {
		this(0L, title, contents);
	}

	public Issue(long id, String title, String contents) {
		super(id);
		this.title = title;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public Issue setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getContents() {
		return contents;
	}

	public Issue setContents(String contents) {
		this.contents = contents;
		return this;
	}


}
