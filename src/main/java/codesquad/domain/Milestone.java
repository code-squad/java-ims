package codesquad.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Milestone {
	@Id
	@GeneratedValue
	@JsonProperty
	private long id;
	
	@Size(min = 3, max = 50)
	@Column(nullable = false)
	@JsonProperty
	private String subject;
	
	@Column(nullable = false)
	@JsonProperty
	private String startDate;
	
	@Column(nullable = false)
	@JsonProperty
	private String endDate;
	
	@OneToMany
	@JsonIgnore
	private List<Issue> issues;
	
	@JsonProperty
	private boolean deleted = false;
	
	public Milestone() {
	}
	
	public Milestone(String subject, String startDate, String endDate) {
		this.subject = subject;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Milestone deleteIssue(Issue issue) {
		issues.remove(issue);
		return this;
	}
	
	public boolean checkContain(Issue issue) {
		if (this.issues.contains(issue)) {
			return true;
		}
		return false;
	}
	
	public boolean isDeleted() {
		return this.deleted;
	}
	
	//getter, setter methods
	public long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}
	
	public String getStartDate() {
		return startDate;
	}
	
	public String getEndDate() {
		return endDate;
	}
	
	public List<Issue> getIssues() {
		return issues;
	}
	
	public Issue getIssue(long id) {
		for (Issue issue : issues) {
			if (issue.getId() == id) {
				return issue;
			}
		}
		return null;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}
	
	//toString method
	@Override
	public String toString() {
		return "Milestone [subject=" + subject + ", startDate=" + startDate + ", endDate=" + endDate + ", deleted=" + deleted + "]";
	}
}
