package codesquad.domain;

import codesquad.exception.UnAuthenticationException;
import codesquad.exception.UnAuthorizedException;
import codesquad.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.util.Objects;

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

    public User updateUser(User loginUser, User updatingUser) {
        isOwner(loginUser);
        this.password = updatingUser.password;
        this.name = updatingUser.name;
        return this;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isLogin(User loginUser) {
        if (Objects.isNull(loginUser)) {
            throw new UnAuthenticationException();
        }
        return true;
    }

    public boolean isOwner(User accessor) {
        isLogin(accessor);
        if (!this.equals(accessor)) {
            throw new UnAuthorizedException();
        }
        return true;
    }

    public UserDto _toUserDto() {
        return new UserDto(this.userId, this.password, this.name);
    }

    public UserDto getUserDto() {
        return this._toUserDto();
    }

    @JsonIgnore
    public boolean isGuestUser() {
        return false;
    }

    public static class GuestUser extends User {
        @Override
        public boolean isGuestUser() {

            return true;
        }
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + ", id = "+ getId() +"]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return _toUserDto().getUserId().equals(user._toUserDto().getUserId()) &&
                _toUserDto().getPassword().equals(user._toUserDto().getPassword()) &&
                getId() == user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _toUserDto().getUserId(), _toUserDto().getPassword());
    }
}
