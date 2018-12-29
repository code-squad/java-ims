package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class IssueTest {
    public static final Issue ISSUE_JAVAJIGI = new Issue("javajigi's issue", "javajigi contents", JAVAJIGI);
    public static final Issue ISSUE_SANJIGI = new Issue("sanjigi's issue", "sanjigi contents", SANJIGI);

    @Before
    public void set_writer() {
        ISSUE_JAVAJIGI.writeBy(JAVAJIGI);
        ISSUE_SANJIGI.writeBy(SANJIGI);
    }

    @Test
    public void match_writer() throws Exception {
        assertTrue(ISSUE_JAVAJIGI.isMatchWriter(JAVAJIGI));
    }

    @Test
    public void mismatch_writer() throws Exception {
        assertFalse(ISSUE_JAVAJIGI.isMatchWriter(SANJIGI));
    }

    @Test
    public void update_owner() throws Exception {
        Issue origin = ISSUE_JAVAJIGI;
        Issue target = new Issue("updated subject", "updated comment", JAVAJIGI);
        origin.update(JAVAJIGI, target);
        assertThat(origin, is(target));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() throws Exception {
        Issue origin = ISSUE_JAVAJIGI;
        Issue target = new Issue("updated subject", "updated comment", JAVAJIGI);
        origin.update(SANJIGI, target);
    }
}
