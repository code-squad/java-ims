package codesquad.domain;

import codesquad.UnAuthorizedException;
import codesquad.domain.user.ProfileImage;
import codesquad.domain.user.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name", ProfileImage.DEFAULT_IMAGE);
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name", ProfileImage.DEFAULT_IMAGE);

    public static User newUser(Long id) {
        return new User(id, "userId", "pass", "name", ProfileImage.DEFAULT_IMAGE);
    }

    public static User newUser(String userId) {
        return newUser(userId, "password");
    }

    public static User newUser(String userId, String password) {
        return new User(1L, userId, password, "name", ProfileImage.DEFAULT_IMAGE);
    }

    @Test
    public void update_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = origin;
        User target = new User("sanjigi", "password", "name2", ProfileImage.DEFAULT_IMAGE);
        origin.update(loginUser, target);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = newUser("javajigi");
        User target = new User("sanjigi", "password", "name2", ProfileImage.DEFAULT_IMAGE);
        origin.update(loginUser, target);
    }

    @Test
    public void update_match_password() {
        User origin = newUser("sanjigi");
        User target = new User("sanjigi", "password", "name2", ProfileImage.DEFAULT_IMAGE);
        origin.update(origin, target);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test
    public void update_mismatch_password() {
        User origin = newUser("sanjigi", "password");
        User target = new User("sanjigi", "password2", "name2", ProfileImage.DEFAULT_IMAGE);
        origin.update(origin, target);
        assertThat(origin.getName(), is(not(target.getName())));
    }

    @Test
    public void match_password() throws Exception {
        User user = newUser("sanjigi");
        assertTrue(user.matchPassword(user.getPassword()));
    }

    @Test
    public void mismatch_password() throws Exception {
        User user = newUser("sanjigi");
        assertFalse(user.matchPassword(user.getPassword() + "2"));
    }
}
