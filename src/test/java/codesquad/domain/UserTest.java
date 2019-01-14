package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class UserTest {
    public static User newUser(String userId) {
        return newUser(userId, "password");
    }

    public static User newUser(String userId, String password) {
        return new User(1L, userId, password, "name");
    }

    public static Attachment attachment = new AttachmentDummy();

    @Test
    public void update_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = origin;
        User target = new User("sanjigi", "password", "name2");
        origin.update(loginUser, target, attachment);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = newUser("javajigi");
        User target = new User("sanjigi", "password", "name2");
        origin.update(loginUser, target, attachment);
    }

    @Test
    public void update_match_password() {
        User origin = newUser("sanjigi");
        User target = new User("sanjigi", "password", "name2");
        origin.update(origin, target, attachment);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test
    public void update_mismatch_password() {
        User origin = newUser("sanjigi", "password");
        User target = new User("sanjigi", "password2", "name2");
        origin.update(origin, target, attachment);
        assertTrue(origin.getName().equals(target.getName()));
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
