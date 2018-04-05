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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {
	private static final Logger log = LoggerFactory.getLogger(Issue.class);
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
	
	@OneToMany
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_answers"))
	private List<Answer> answers;

	private boolean deleted = false;

	public Issue() {
	}

	public Issue(String title, String contents) {
		this(0L, title, contents);
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
		log.info("otherUser : {}", loginUser);
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
	
	
	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswer(Answer answer) {
		answers.add(answer);
	}
	

	public IssueDto toIssueDto() {
		return new IssueDto(getId(), title, contents);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((assignee == null) ? 0 : assignee.hashCode());
		result = prime * result + ((contents == null) ? 0 : contents.hashCode());
		result = prime * result + (deleted ? 1231 : 1237);
		result = prime * result + ((labels == null) ? 0 : labels.hashCode());
		result = prime * result + ((milestone == null) ? 0 : milestone.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((writer == null) ? 0 : writer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Issue other = (Issue) obj;
		if (assignee == null) {
			if (other.assignee != null)
				return false;
		} else if (!assignee.equals(other.assignee))
			return false;
		if (contents == null) {
			if (other.contents != null)
				return false;
		} else if (!contents.equals(other.contents))
			return false;
		if (deleted != other.deleted)
			return false;
		if (labels == null) {
			if (other.labels != null)
				return false;
		} else if (!labels.equals(other.labels))
			return false;
		if (milestone == null) {
			if (other.milestone != null)
				return false;
		} else if (!milestone.equals(other.milestone))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (writer == null) {
			if (other.writer != null)
				return false;
		} else if (!writer.equals(other.writer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Issue [title=" + title + ", contents=" + contents + ", writer=" + writer + ", assignee=" + assignee
				+ ", milestone=" + milestone + ", labels=" + labels + ", deleted=" + deleted + "]";
	}


}
