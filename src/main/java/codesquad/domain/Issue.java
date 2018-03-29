package codesquad.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {

	@Size(min = 6, max = 30)
	@Column(nullable = false, length = 20)
	@JsonIgnore
	private String title;

	@Size(min = 3, max = 30)
	@Column(nullable = false, length = 20)
	private String contents;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_assignee"))
	private User assignee;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
	private Milestone milestone;
	
	@ManyToMany
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_labels"))
	private List<Label> labels;

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

	public User getWriter() {
		return writer;
	}

	public void writeBy(User loginUser) {
		writer = loginUser;
	}

	public boolean isWriter(User loginUser) {
		return writer.equals(loginUser);
	}

	public void update(User loginUser, IssueDto issueDto) {
		if (!isWriter(loginUser))
			throw new IllegalStateException("자신의 질문만 수정할 수 있습니다");
		this.title = issueDto.getTitle();
		this.contents = issueDto.getContents();
	}

	public void delete(User loginUser) {
		if (!isWriter(loginUser))
			throw new IllegalStateException("자신의 질문만 삭제할 수 있습니다");
		this.deleted = true;
	}

	public boolean isDeleted() {
		return deleted;
	}
	
	public Milestone getMilestone() {
		return milestone;
	}

	public void setMilestone(Milestone milestone) {
		this.milestone = milestone;
	}
	
	public User getAssignee() {
		return this.assignee;
	}
	
	public void setAssignee(User assignee) {
		this.assignee = assignee;
	}
	
	public void setLabel(Label label) {
		labels.add(label);
	}
	
	public List<Label> getLabels() {
		return labels;
	}

	public IssueDto toIssueDto() {
		return new IssueDto(getId(), title, contents);
	}

	@Override
	public String toString() {
		return "Issue [title=" + title + ", contents=" + contents + ", writer=" + writer + ", assignee=" + assignee
				+ ", milestone=" + milestone + ", labels=" + labels + ", deleted=" + deleted + "]";
	}
	

}
