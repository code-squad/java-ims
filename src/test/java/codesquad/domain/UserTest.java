package codesquad.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import codesquad.UnAuthorizedException;

public class UserTest {
    public static final User JAVAJIGI = new User("javajigi", "password", "name");
    public static final User SANJIGI = new User("sanjigi", "password", "name");

    public static User newUser(Long id) {
        return new User("userId", "pass", "name");
    }

    public static User newUser(String userId) {
        return newUser(userId, "password");
    }

    public static User newUser(String userId, String password) {
        return new User(userId, password, "name");
    }

    @Test
    public void update_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = origin;
        User target = new User("sanjigi", "password", "name2");
        origin.update(loginUser._toUserDto(), target._toUserDto());
        assertThat(origin._toUserDto().getName(), is(target._toUserDto().getName()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = newUser("javajigi");
        User target = new User("sanjigi", "password", "name2");
        origin.update(loginUser._toUserDto(), target._toUserDto());
    }

    @Test
    public void update_match_password() {
        User origin = newUser("sanjigi");
        User target = new User("sanjigi", "password", "name2");
        origin.update(origin._toUserDto(), target._toUserDto());
        assertThat(origin._toUserDto().getName(), is(target._toUserDto().getName()));
    }

    @Test
    public void update_mismatch_password() {
        User origin = newUser("sanjigi", "password");
        User target = new User("sanjigi", "password2", "name2");
        origin.update(origin._toUserDto(), target._toUserDto());
        assertThat(origin._toUserDto().getName(), is(not(target._toUserDto().getName())));
    }
    
    @Test
    public void match_password() throws Exception {
        User user = newUser("sanjigi");
        assertTrue(user.matchPassword(user._toUserDto().getPassword()));
    }
    
    @Test
    public void mismatch_password() throws Exception {
        User user = newUser("sanjigi");
        assertFalse(user.matchPassword(user._toUserDto().getPassword() + "2"));
    }
}
