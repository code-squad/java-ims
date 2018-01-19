package codesquad.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import codesquad.dto.LabelDto;
import support.domain.AbstractEntity;

@Entity
public class Label extends AbstractEntity {
	@Column(nullable = false, length = 20)
	private String subject;

	@ManyToMany(mappedBy = "labels")
	private Set<Issue> issues = new HashSet<>();

	public Label() {

	}

	public Label(String subject) {
		this(0L, subject);
	}

	public Label(long id, String subject) {
		super(id);
		this.subject = subject;
	}

	public LabelDto _toLabelDto() {
		return new LabelDto(this.subject);
	}

	public void update(String subject) {
		this.subject = subject;
	}

	public String getSubject() {
		return subject;
	}

	@Override
	public String toString() {
		return "Label [subject=" + subject + ", issues=" + issues + "]";
	}
}
