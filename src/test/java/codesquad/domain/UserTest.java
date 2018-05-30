package codesquad.domain;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;

public class UserTest {
    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name");

    public static User newUser(Long id) {
        return new User(id, "userId", "pass", "name");
    }

    public static User newUser(String userId) {
        return newUser(userId, "password");
    }

    public static User newUser(String userId, String password) {
        return new User(1L, userId, password, "name");
    }

    @Test
    public void update_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = origin;
        User target = new User("sanjigi", "password", "name2");
        origin.update(loginUser, target);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        User origin = newUser("sanjigi");
        User loginUser = newUser("javajigi");
        User target = new User("sanjigi", "password", "name2");
        origin.update(loginUser, target);
    }

    @Test
    public void update_match_password() throws UnAuthenticationException {
        User origin = newUser("sanjigi");
        User target = new User("sanjigi", "password", "name2");
        origin.update(origin, target);
        assertThat(origin.getName(), is(target.getName()));
    }

    @Test(expected=UnAuthenticationException.class)
    public void update_mismatch_password() throws UnAuthorizedException, UnAuthenticationException {
        User origin = newUser("sanjigi", "password");
        User target = new User("sanjigi", "password2", "name2");
        origin.update(origin, target);
    }
    
    /*@Test
    public void match_password() throws Exception {
        User user = newUser("sanjigi", "test");
        assertThat(user.matchPassword("test"), is(true));
    }*/
    
    @Test(expected=UnAuthenticationException.class)
    public void mismatch_password() throws Exception {
        User user = newUser("sanjigi");
       user.matchPassword(user.getPassword() + "2");
    }
}
