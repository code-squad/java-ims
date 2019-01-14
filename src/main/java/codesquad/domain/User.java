package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import support.domain.AbstractEntity;
import javax.persistence.*;
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

    @Embedded
    private Attachment avatar;

    public User() {

    }

    public User(String userId, String password, String name) {
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public User(long id, String userId, String password, String name) {
        super(id);
        this.userId = userId;
        this.password = password;
        this.name = name;
    }

    public User(String userId, String password, String name, Attachment avatar) {
        this(userId, password, name);
        this.avatar = avatar;
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

    public Attachment getAvatar() {
        return avatar;
    }

    public void setAvatar(Attachment avatar) {
        this.avatar = avatar;
    }

    private boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }

    public User update(User loginUser, User target, Attachment avatar) {
        if(!loginUser.userId.equals(target.userId)) {
            throw new UnAuthorizedException();
        }

        if(!avatar.isDummyAttachment()) {
            this.avatar = avatar;
        }

        this.name = target.name;
        this.password = target.password;
        return this;
    }

    public void update(UserDto target) {
        this.name = target._toUser().name;
        this.password = target._toUser().password;
    }

    public boolean matchPassword(String password) {
        return this.password.equals(password);
    }

    public UserDto _toUserDto() {
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
        return "User{" +
                "userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", avatar=" + avatar +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(password, user.password) &&
                Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), userId, password, name);
    }
}
