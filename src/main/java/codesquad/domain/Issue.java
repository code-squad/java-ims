package codesquad.domain;

import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import support.domain.AbstractEntity;

@Entity
public class Issue extends AbstractEntity {

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_parent_id"))
	private User loginUser;

	@ManyToOne
	@JoinColumn(foreignKey = @ForeignKey(name = "fk_issue_mileStone_id"))
	private MileStone mileStone;

	@Size(min = 3, max = 20)
	@Column(nullable = false, length = 20)
	private String subject;

	@ManyToOne
	private User assignedUser;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "issue_label", joinColumns = @JoinColumn(name = "issue_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
	private Set<Label> labels = Sets.newHashSet();

	@Lob
	@Size(min = 3)
	private String comment;

	public Issue() {

	}

	public Issue(User loginUser, String subject, String comment) {
		this(0L, loginUser, subject, comment);
	}

	public Issue(long id, User loginUser, String subject, String comment) {
		super(id);
		this.loginUser = loginUser;
		this.subject = subject;
		this.comment = comment;
	}

	public IssueDto _toIssueDto() {
		return new IssueDto(this.loginUser, this.subject, this.comment);
	}

	public void setMileStone(MileStone mileStone) {
		this.mileStone = mileStone;
	}

	public void setAssignedUser(User assignedUser) {
		this.assignedUser = assignedUser;
	}

	public void addLabel(Label label) {
		labels.add(label);
	}

	public Set<Label> getLabels() {
		return labels;
	}

	public User getAssignedUser() {
		return assignedUser;
	}

	public MileStone getMileStone() {
		return mileStone;
	}

	public boolean isSameUser(User loginUser) {
		return this.loginUser.equals(loginUser);
	}

	public void update(User loginUser, String subject, String comment) {
		if (!isSameUser(loginUser)) {
			throw new UnAuthorizedException();
		}
		this.subject = subject;
		this.comment = comment;
	}

	public static String getBlank(String blank) {
		if (StringUtils.isBlank(blank)) {
			return blank;
		}
		return null;
	}

	public static String getOptionalBlank(String blank) {
		return Optional.ofNullable(getBlank(blank)).orElse("");
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

	public User getLoginUser() {
		return loginUser;
	}

	public void setLabel(Label label) {
		this.labels.add(label);
	}
}
