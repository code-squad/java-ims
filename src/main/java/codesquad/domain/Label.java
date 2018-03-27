package codesquad.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import support.domain.AbstractEntity;

@Entity
public class Label extends AbstractEntity{

	@Size(min = 1, max = 20)
	@Column(unique = true, nullable = false, length = 20)
	String title;
	
	@Column(nullable = false)
	String color;
	
	@ManyToMany(mappedBy = "labels")
	private List<Issue> issues = new ArrayList<>();
	
	public Label() {
	}
	
	public Label(String title, String color) {
		this.title = title;
		this.color = color;
	}

	public Label(long id, String title, String color) {
		super(id);
		this.title = title;
		this.color = color;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setIssue(Issue issue) {
		issues.add(issue);
	}
	
	public List<Issue> getIssues() {
		return issues;
	}
	
	public void update(Label updateLabel) {
		this.title = updateLabel.getTitle();
		this.color = updateLabel.getColor();
	}
	
	@Override
	public String toString() {
		return "Label [title=" + title + ", color=" + color + "]";
	}



}
