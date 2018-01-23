package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import codesquad.dto.LabelDto;
import support.domain.AbstractEntity;

@Entity
public class Label extends AbstractEntity {
	@Size(min = 3, max = 20)
	@Column(nullable = false)
	private String title;
	
	public Label() {
	}
	
	public Label(String title) {
		this(0L, title);
	}
	
	public Label(Long id, String title) {
		super(id);
		this.title = title;
	}
	
	public void update(LabelDto labelDto) {
		this.title = labelDto.getTitle();
	}
	
	public LabelDto _toLabelDto() {
		return new LabelDto(super.getId(), this.title);
	}

	@Override
	public String toString() {
		return "Label [title=" + title + "]";
	}
}
