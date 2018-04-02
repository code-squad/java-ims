package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;
//이슈 추가, 수정, 삭제
//로그인한 사용자에 한해 이슈를 추가할 수 있도록 한다.
//자신이 생성한 이슈에 한해서 수정과 삭제를 할 수 있도록 한다.
//이슈 목록, 상세 페이지에서 이슈를 생성한 사람을 보여주어야 한다.
@Entity
public class Issue extends AbstractEntity {
	@Size(min = 3, max = 20)
	@Column(unique = true, nullable = false, length = 20)
	private String subject;

	@Size(min = 6, max = 20)
	@Column(nullable = false)
	private String comment;
	
	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_writer"))
	private User writer;

	public Issue() {
	}

	public Issue(String subject, String comment, User user) {
		this(0L, subject, comment, user);
	}
	
	public Issue(Long id, String subject, String comment, User user) {
		super(id);
		this.subject = subject;
		this.comment = comment;
		this.writer = user;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public IssueDto _toIssueDto() {
		return new IssueDto(subject, comment, writer);
	}

	public boolean matchWriter(User loginUser) {
		return loginUser.equals(writer);
	}

	public void update(Issue newIssue) {
		this.subject = newIssue.getSubject();
		this.comment = newIssue.getComment();
	}

}
