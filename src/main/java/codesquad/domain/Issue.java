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
import javax.validation.constraints.Size;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
	private Milestone milestone;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	@ManyToMany
//	@ManyToMany(fetch = FetchType.EAGER)
//	Issue가 호출 될 때 Label들의 정보도 같이 가지고 와서 뿌려준다.
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_label"))
	@JoinTable(name = "ISSUE_LABEL", joinColumns = @JoinColumn(name = "ISSUE_ID"), inverseJoinColumns = @JoinColumn(name = "LABEL_ID"))
	private List<Label> label;

	@Size(max = 20)
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
	
	public Issue(String title, String contents, User writer, Milestone milestone, List<Label> label) {
		this(0L, title, contents, writer, milestone, label);
	}

	public Issue(Long id, String title, String contents, User writer) {
		super(id);
		this.title = title;
		this.contents = contents;
		this.writer = writer;
	}
	
	public Issue(Long id, String title, String contents, User writer, Milestone milestone, List<Label> label) {
		super(id);
		this.title = title;
		this.contents = contents;
		this.writer = writer;
		this.milestone = milestone;
		this.label = label;
	}

	public boolean isSameWriter(User loginUser) {
		return this.writer.equals(loginUser);
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
		this.writer = user;
	}
	
	public IssueDto _toIssueDto() {
		return new IssueDto(super.getId(), this.writer, this.title, this.contents, this.milestone, this.label);
	}

	@Override
	public String toString() {
		return "Issue [title=" + title + ", contents=" + contents + "]";
	}
}
