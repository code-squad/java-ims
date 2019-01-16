package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class UserTest {

    public static Attachment attachment = new AttachmentDummy();

    @Test
    public void update_owner() throws Exception {
        User origin = new User.UserBuilder("sanjigi", "password", "name").build();
        User target = new User.UserBuilder("sanjigi", "password", "different").build();
        origin.update(origin, target, attachment);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = new User.UserBuilder("sanjigi", "password", "name").build();
        User target = new User.UserBuilder("anotherUser", "password", "name").build();
        origin.update(origin, target, attachment);
    }

    @Test
    public void update_match_password() {
        User origin = new User.UserBuilder("javajigi", "password", "name").build();
        User target = new User.UserBuilder("javajigi", "password", "name_modify").build();
        origin.update(origin, target, attachment);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test
    public void update_mismatch_password() {
        User origin = new User.UserBuilder("javajigi", "notMatch", "name").build();
        User target = new User.UserBuilder("javajigi", "password", "name").build();
        origin.update(origin, target, attachment);
        assertTrue(origin.getName().equals(target.getName()));
    }

    @Test
    public void match_password() throws Exception {
        User origin = new User.UserBuilder("javajigi", "password", "name").build();
        User target = new User.UserBuilder("javajigi", "password", "name").build();
        assertTrue(origin.matchPassword(target.getPassword()));
    }

    @Test
    public void mismatch_password() throws Exception {
        User origin = new User.UserBuilder("javajigi", "password", "name").build();
        User target = new User.UserBuilder("javajigi", "modifiedPWD", "name").build();
        assertFalse(origin.matchPassword(target.getPassword()));
    }
}
