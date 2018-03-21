package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {
	
    @Size(min = 6, max = 20)
    @Column(nullable = false, length = 20)
    @JsonIgnore
    private String title;

    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 20)
    private String contents;
    
    
    private boolean deleted = false;

	public Issue() {
	}
	
	public Issue(String title, String contents) {
		this.title = title;
		this.contents = contents;
	}

	public Issue(long id, String title, String contents) {
		super(id);
		this.title = title;
		this.contents = contents;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}
	
	public IssueDto toIssueDto() {
		return new IssueDto(getId(), title, contents);
	}

	@Override
	public String toString() {
		return "Issue [issueId=" + getId() + ", title=" + title + ", contents=" + contents + "]";
	}
    
}
