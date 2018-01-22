package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import support.domain.AbstractEntity;

@Entity
public class Label extends AbstractEntity {
	@Column(nullable = false)
	private String title;
	
	public Label() {
	}
	
	public Label(String label) {
		this(0L, label);
	}
	
	public Label(Long id, String label) {
		super(id);
		this.title = label;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void update(Label label) {
		this.title = label.getTitle();
	}
}
