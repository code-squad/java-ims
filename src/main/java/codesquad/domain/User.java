package codesquad.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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

    public User() {
    }

    public User(String userId, String password, String name) {
        super(0L);
        this.userId = userId;
        this.password = password;
        this.name = name;
    }
    
    private boolean matchUserId(String userId) {
        return this.userId.equals(userId);
    }
    
    public void update(UserDto loginUser, UserDto target) {
        if (!matchUserId(loginUser.getUserId())) {
            throw new UnAuthorizedException();
        }

        if (!matchPassword(target.getPassword())) {
            return;
        }

        this.name = target.getName();
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
        return "User [userId=" + userId + ", password=" + password + ", name=" + name + "]";
    }
}
