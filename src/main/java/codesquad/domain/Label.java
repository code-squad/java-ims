package codesquad.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import codesquad.UnAuthenticationException;

@Entity
public class Label {
	@Id
	@GeneratedValue
	private long id;
	
	@Size(min = 3, max = 100)
	@Column(unique = false, nullable = false, length = 30)
	private String subject;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "label_writer"))
	private User writer;
	
	@OneToMany
	private List<Issue> issues;
	
	private boolean deleted = false;

	public Label() {
	}
	
	public Label(String subject) {
		this.subject = subject;
	}
	
	public void update(User loginUser, String subject) throws UnAuthenticationException {
		if (!this.isOwner(loginUser)) {
			throw new UnAuthenticationException();
		}
		this.subject = subject;
	}
	
	public void delete(User loginUser) throws UnAuthenticationException {
		if (!this.isOwner(loginUser)) {
			throw new UnAuthenticationException();
		}
		this.deleted = true;
	}
	
	public Label addIssue(Issue issue) {
		issues.add(issue);
		return this;
	}
	
	public boolean isOwner(User loginUser) {
		return this.writer.equals(loginUser);
	}
	
	public void writedBy(User loginUser) {
		this.writer = loginUser;
	}
	
	//getter() setter() methods
	public long getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public User getWriter() {
		return writer;
	}
}
