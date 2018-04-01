package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {
	@Size(min = 3, max = 20)
	@Column(unique = true, nullable = false, length = 20)
	private String title;

	@Size(min = 6, max = 20)
	@Column(nullable = false)
	private String contents;

	public Issue() {
	}

	public Issue(String title, String contents) {
		super(0L);
		this.title = title;
		this.contents = contents;
	}

}
