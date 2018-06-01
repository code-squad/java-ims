package codesquad.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import org.hibernate.annotations.Where;

import support.domain.AbstractEntity;

@Entity
public class MileStone extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	@Column(nullable = false)
	private LocalDateTime startDate;

	@Column(nullable = false)
	private LocalDateTime endDate;

	@Column(nullable = false)
	private String subject;

	@OneToMany(mappedBy = "mileStone")
	@Where(clause = "closed = false")
	@OrderBy("id ASC")
	private List<Issue> issues = new ArrayList<>();

	private boolean closed = false;

	public MileStone() {
	}

	public MileStone(String startDate, String endDate, String subject) {
		this.startDate = LocalDateTime.parse(startDate);
		this.endDate = LocalDateTime.parse(endDate);
		this.subject = subject;
	}

	public User getWriter() {
		return writer;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
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

	public String getFormattedStartDate() {
		return startDate.format(ofPattern().withLocale(ofLocale()));
	}

	public String getFormattedEndDate() {
		return endDate.format(ofPattern().withLocale(ofLocale()));
	}

	public DateTimeFormatter ofPattern() {
		return DateTimeFormatter.ofPattern("MMM dd, yyyy");
	}

	public int getOpenIssue() {
		int open = 0;
		for (int i = 0; i < issues.size(); i++) {
			if (!issues.get(i).isClosed()) {
				open++;
			}
		}
		return open;
	}

	public int getCloseIssue() {
		int close = 0;
		for (int i = 0; i < issues.size(); i++) {
			if (issues.get(i).isClosed()) {
				close++;
			}
		}
		return close;
	}

	public Locale ofLocale() {
		return Locale.ENGLISH;
	}

	public void writeBy(User loginUser) {
		this.writer = loginUser;
	}

}
