package codesquad.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import support.domain.AbstractEntity;

@Entity
public class MileStone extends AbstractEntity {

	@Size(min = 3, max = 20)
	@Column(unique = true, nullable = false, length = 20)
	private String subject;

	private String startDate;

	private String endDate;

	@OneToMany(mappedBy = "mileStone")
	@JsonIgnore
	private List<Issue> issues;

	public MileStone() {

	}

	public MileStone(String subject, String startDate, String endDate) {
		this(0L, subject, startDate, endDate);
	}

	public MileStone(long id, String subject, String startDate, String endDate) {
		super(id);
		this.subject = subject;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public MileStoneDto _toMileStoneDto() {
		return new MileStoneDto(this.subject, this.startDate, this.endDate);
	}

	public void addIssue(Issue issue) {
		this.issues.add(issue);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public void setIssues(List<Issue> issues) {
		this.issues = issues;
	}

	public int getIssueSize() {
		return this.issues.size();
	}

	@Override
	public String toString() {
		return "MileStone [subject=" + subject + ", startDate=" + startDate + ", endDate=" + endDate + ", issues="
				+ issues + ", toString()=" + super.toString() + "]";
	}

}
