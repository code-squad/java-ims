package codesquad.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
	private Milestone milestone;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User user;

	@ManyToMany
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_label"))
	@JoinTable(name = "ISSUE_LABEL", joinColumns = @JoinColumn(name = "ISSUE_ID"), inverseJoinColumns = @JoinColumn(name = "LABEL_ID"))
	private List<Label> label;

	@Column(nullable = false)
	private String title;

	@Lob
	@Column(nullable = false)
	private String contents;

	public Issue() {
	}

	public Issue(String title, String contents, User writer) {
		this(0L, title, contents, writer);
	}

	public Issue(Long id, String title, String contents, User writer) {
		super(id);
		this.title = title;
		this.contents = contents;
		this.user = writer;
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
		return user;
	}

	public Milestone getMilestone() {
		return milestone;
	}
	
	public User getUser() {
		return user;
	}

	public List<Label> getLabel() {
		return label;
	}

	public boolean isSameWriter(User loginUser) {
		return this.user.equals(loginUser);
	}

	public void update(IssueDto issueDto) {
		this.title = issueDto.getTitle();
		this.contents = issueDto.getContents();
	}

	public void addMilesstone(Milestone milestone) {
		this.milestone = milestone;
	}
	
	public void addLabel(Label label) {
		this.label.add(label);
	}
	
	public void addUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Issue [title=" + title + ", contents=" + contents + "]";
	}
}
