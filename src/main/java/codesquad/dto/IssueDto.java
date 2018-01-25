package codesquad.dto;

import java.util.Set;

import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Sets;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.MileStone;
import codesquad.domain.User;

public class IssueDto {

	private User user;

	@Size(min = 3, max = 20)
	private String subject;

	@Size(min = 3)
	private String comment;

	private MileStone mileStone;

	private User assignedUser;

	@ManyToMany
	private Set<Label> labels = Sets.newHashSet();

	public IssueDto() {

	}

	public IssueDto(User loginUser, String subject, String comment) {
		this.user = loginUser;
		this.subject = subject;
		this.comment = comment;
	}

	public Issue _toIssue() {
		return new Issue(this.user, this.subject, this.comment);
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

	public boolean isSubjectBlank() {
		return StringUtils.isBlank(this.subject);
	}

	public boolean isCommentBlank() {
		return StringUtils.isBlank(this.comment);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public MileStone getMileStone() {
		return mileStone;
	}

	public User getAssignedUser() {
		return assignedUser;
	}

	public Set<Label> getLabels() {
		return labels;
	}

	@Override
	public String toString() {
		return "IssueDto [user=" + user + ", subject=" + subject + ", comment=" + comment + "mileStone=" + mileStone
				+ "]";
	}

}
