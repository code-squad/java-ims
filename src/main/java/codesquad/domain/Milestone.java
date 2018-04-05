package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Size;

@Entity
public class Milestone {
	@Id
	@GeneratedValue
	private long id;
	
	@Size(min = 3, max = 50)
	@Column(nullable = false)
	private String subject;
	
	@Column(nullable = false)
	private String startDate;
	
	@Column(nullable = false)
	private String endDate;
	
	private boolean deleted = false;

	public Milestone() {
	}
	
	public Milestone(String subject, String startDate, String endDate) {
		this.subject = subject;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public boolean isDeleted() {
		return this.deleted;
	}
	
	//getter, setter methods
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
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

	//toString method
	@Override
	public String toString() {
		return "Milestone [subject=" + subject + ", startDate=" + startDate + ", endDate=" + endDate + ", deleted=" + deleted + "]";
	}
	
}
