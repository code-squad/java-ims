package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class UserTest {
    public static final User USER = new User(1L, "brad", "password", "Brad");
    public static final User USER2 = new User(2L, "leejh903", "password", "이정현");

    public static User newUser(Long id) {
        return new User(id, "userId", "password", "name");
    }

    public static User newUser(String userId) {
        return newUser(userId, "password");
    }

    public static User newUser(String userId, String password) {
        return new User(1L, userId, password, "name");
    }

    @Test
    public void update_owner() throws Exception {
        User origin = newUser("brad");
        User loginUser = origin;
        User target = new User("brad", "password", "name2");
        origin.update(loginUser, target);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = newUser("leejh903");
        User loginUser = newUser("brad903");
        User target = new User("leejh903", "password", "name2");
        origin.update(loginUser, target);
    }

    @Test
    public void update_match_password() {
        User origin = newUser("leejh903");
        User target = new User("leejh903", "password", "name2");
        origin.update(origin, target);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test
    public void update_mismatch_password() {
        User origin = newUser("leejh903", "password");
        User target = new User("leejh903", "password2", "name2");
        origin.update(origin, target);
        assertThat(origin.getName(), is(not(target.getName())));
    }

    @Test
    public void match_password() throws Exception {
        User user = newUser("leejh903");
        assertTrue(user.matchPassword(user.getPassword()));
    }

    @Test
    public void mismatch_password() throws Exception {
        User user = newUser("leejh903");
        assertFalse(user.matchPassword(user.getPassword() + "2"));
    }
}
