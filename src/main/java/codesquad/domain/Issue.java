package codesquad.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Issue {
	@Id
	@GeneratedValue
	private long id;

	@Size(min = 3, max = 100)
	@Column(unique = false, nullable = false, length = 100)
	private String title;

	@Size(min = 3, max = 300)
	@Column(nullable = false, length = 300)
	private String contents;

	private boolean deleted = false;

	public Issue() {
	}

	public Issue(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getContents() {
		return contents;
	}
}
