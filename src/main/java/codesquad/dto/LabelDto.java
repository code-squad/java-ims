package codesquad.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import codesquad.domain.Label;

public class LabelDto {
	private long id;
	@NotNull
	@Size(min = 3, max = 20)
	private String title;
	
	
	public LabelDto() {
	}
	
	public LabelDto(long id, String title) {
		this.id = id;
		this.title = title;
	}
	
	public Label _toLabel() {
		return new Label(this.title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "LabelDto [title=" + title + "]";
	}
}
