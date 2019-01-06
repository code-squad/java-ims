package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Test;
import org.slf4j.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.slf4j.LoggerFactory.getLogger;

public class UserTest {
    private static final Logger log = getLogger(UserTest.class);

    public static final User JAVAJIGI = new User(1L, "javajigi", "password", "name");
    public static final User SANJIGI = new User(2L, "sanjigi", "password", "name");

    public static final User UPDATING_USER = new User("javajigi", "password2", "name2");

    @Test
    public void update_owner() throws Exception {
        UserTest.JAVAJIGI.updateUser(UserTest.JAVAJIGI, UPDATING_USER);
        assertThat(UserTest.JAVAJIGI._toUserDto().getName(), is(UPDATING_USER._toUserDto().getName()));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_by_other_user() throws Exception {
        UserTest.SANJIGI.updateUser(UserTest.JAVAJIGI, UPDATING_USER);

//      sanjigi의 유저정보를 javajigi가 updagingUser정보로 변경하려고 한다.
    }

    @Test
    public void match_password() throws Exception {
        assertTrue(JAVAJIGI.matchPassword(JAVAJIGI._toUserDto().getPassword()));
    }

    @Test
    public void mismatch_password() throws Exception {
        assertFalse(UPDATING_USER.matchPassword(SANJIGI._toUserDto().getPassword()));
    }
}
