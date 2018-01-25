package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;

import codesquad.dto.LabelDto;
import support.domain.AbstractEntity;

@Entity
public class Label extends AbstractEntity {
	@Column(nullable = false, length = 20)
	private String title;
	
	public Label() {
	}

	public Label(String title) {
		super(0L);
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
