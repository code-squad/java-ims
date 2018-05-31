package codesquad.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Where;

import support.domain.AbstractEntity;

@Entity
public class MileStone extends AbstractEntity {

	
	@Column(nullable = false)
	private LocalDateTime startDateTime;
	
	@Column(nullable = false)
	private LocalDateTime closeDateTime;

	
	@Column(nullable = false)
	private String subject;
	
	@OneToMany(mappedBy = "mileStone")
	@Where(clause = "closed = false")
	@OrderBy("id ASC")
	private List<Issue> issues = new ArrayList<>();

	private boolean closed = false;
	
	
	public MileStone() {
	}
	
	
	public MileStone(LocalDateTime startDateTime, LocalDateTime closeDateTime, String subject) {
		this.startDateTime = startDateTime;
		this.closeDateTime = closeDateTime;
		this.subject = subject;
	}
	
	
	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public LocalDateTime getCloseDateTime() {
		return closeDateTime;
	}

	public String getSubject() {
		return subject;
	}

	public List<Issue> getIssues() {
		return issues;
	}

	public boolean isClosed() {
		return closed;
	}

}
