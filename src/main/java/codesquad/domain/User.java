package codesquad.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import codesquad.UnAuthorizedException;
import codesquad.dto.UserDto;
import support.domain.AbstractEntity;

@Entity
public class User extends AbstractEntity {
	public static final GuestUser GUEST_USER = new GuestUser();

	@Size(min = 3, max = 20)
	@Column(unique = true, nullable = false, length = 20)
	private String userId;

	@Size(min = 6, max = 20)
	@Column(nullable = false, length = 20)
	@JsonIgnore
	private String password;

	@Size(min = 3, max = 20)
	@Column(nullable = false, length = 20)
	private String name;

	@OneToMany(mappedBy = "assignedUser")
	private List<Issue> assignedIssues;

	public User() {
	}

	public User(String userId, String password, String name) {
		this(0L, userId, password, name);
	}

	public User(long id, String userId, String password, String name) {
		super(id);
		this.userId = userId;
		this.password = password;
		this.name = name;
	}

	public void addIssue(Issue issue) {
		this.assignedIssues.add(issue);
	}

	public List<Issue> getAssignedIssues() {
		return assignedIssues;
	}

	public String getUserId() {
		return userId;
	}

	public User setUserId(String userId) {
		this.userId = userId;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public User setPassword(String password) {
		this.password = password;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	private boolean matchUserId(String userId) {
		return this.userId.equals(userId);
	}

	public void update(User loginUser, User target) {// user name 만 바꿀수 있도록 설정.
		if (!matchUserId(loginUser.getUserId())) {
			throw new UnAuthorizedException();
		}

		if (!matchPassword(target.getPassword())) {
			return;
		}

		this.name = target.name;
	}

	public boolean matchPassword(String password) {
		return this.password.equals(password);
	}

	public UserDto _toUserDto() {// user 객체를 userDto 객체로 바꿔주는 역할.
		return new UserDto(this.userId, this.password, this.name);
	}

	@JsonIgnore
	public boolean isGuestUser() {
		return false;
	}

	private static class GuestUser extends User {
		@Override
		public boolean isGuestUser() {
			return true;
		}
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", password=" + password + ", name=" + name + "]";
	}
}
