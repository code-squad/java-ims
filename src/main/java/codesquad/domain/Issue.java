package codesquad.domain;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.springframework.context.support.MessageSourceAccessor;

import codesquad.InputDataNullException;
import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {
	@Resource(name = "messageSourceAccessor")
	private MessageSourceAccessor msa;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_milestone"))
	private Milestone milestone;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	@ManyToMany
	// @ManyToMany(fetch = FetchType.EAGER)
	// Issue가 호출 될 때 Label들의 정보도 같이 가지고 와서 뿌려준다.
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_label"))
	@JoinTable(name = "ISSUE_LABEL", joinColumns = @JoinColumn(name = "ISSUE_ID"), inverseJoinColumns = @JoinColumn(name = "LABEL_ID"))
	private List<Label> labels;

	@Column(nullable = false, length = 20)
	private String title;

	@Lob
	@Column(nullable = false)
	private String contents;

	public Issue() {
	}

	public Issue(String title, String contents, User writer, Milestone milestone, List<Label> label) {
		super(0L);
		this.title = title;
		this.contents = contents;
		this.writer = writer;
		this.milestone = milestone;
		this.labels = label;
	}

	public boolean isSameWriter(User loginUser) {
		return this.writer.equals(loginUser);
	}

	public void update(User loginUser, IssueDto issueDto) {
		if (!isSameWriter(loginUser)) {
			throw new UnAuthorizedException();
		}

		this.title = issueDto.getTitle();
		this.contents = issueDto.getContents();
	}

	public void addMilesstone(Milestone milestone) {
		this.milestone = milestone;
	}

	public void addLabelIntoLabels(Label label) {
		this.labels.add(label);
	}

	public void addUser(User user) {
		if (user == null) {
			throw new InputDataNullException(msa.getMessage("nullError"));
		}
		this.writer = user;
	}

	public IssueDto _toIssueDto() {
		return new IssueDto(super.getId(), this.writer, this.title, this.contents, this.milestone, this.labels);
	}

	public Result valid(User loginUser) {
		if (!isSameWriter(loginUser)) {
			return Result.fail("자신이 쓴 글만 수정, 삭제가 가능합니다.");
		}
		return Result.ok();
	}

	@Override
	public String toString() {
		return "Issue [title=" + title + ", contents=" + contents + "]";
	}
}
