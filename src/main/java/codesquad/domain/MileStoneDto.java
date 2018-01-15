package codesquad.domain;

import java.util.List;

import javax.validation.constraints.Size;

public class MileStoneDto {
	@Size(min = 3, max = 20)
	private String subject;
	private String startDate;
	private String endDate;
	private List<Issue> issues;
	
	public MileStoneDto() {
		
	}

	public MileStoneDto(String subject, String startDate, String endDate) {
		this.subject = subject;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public MileStone _toMileStone() {
		return new MileStone(this.subject, this.startDate, this.endDate);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + ((issues == null) ? 0 : issues.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
		result = prime * result + ((subject == null) ? 0 : subject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MileStoneDto other = (MileStoneDto) obj;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (issues == null) {
			if (other.issues != null)
				return false;
		} else if (!issues.equals(other.issues))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (subject == null) {
			if (other.subject != null)
				return false;
		} else if (!subject.equals(other.subject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MileStoneDto [subject=" + subject + ", startDate=" + startDate + ", endDate=" + endDate + ", issues="
				+ issues + "]";
	}
	
	

}
