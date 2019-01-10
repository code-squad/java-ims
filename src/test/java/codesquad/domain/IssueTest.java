package codesquad.domain;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.dto.IssueDto;
import org.junit.Before;
import org.junit.Test;
import support.test.BaseTest;

import java.util.ArrayList;
import java.util.List;

import static codesquad.domain.UserTest.JAVAJIGI;
import static codesquad.domain.UserTest.SANJIGI;

public class IssueTest extends BaseTest {
    public static final List<Issue> ISSUES = new ArrayList<>();
    public static final Issue ISSUE1 = new Issue(1,"testSubject1", "testComment1", JAVAJIGI, false, false, JAVAJIGI);
    public static final Issue ISSUE2 = new Issue(2, "testSubject2", "testComment2", JAVAJIGI, false, false, SANJIGI);
    public static final Issue ISSUE3 = new Issue(3, "testSubject3", "testComment3", SANJIGI, false, false, SANJIGI);
    public static final Issue ISSUE4 = new Issue(4, "testSubject4", "testComment4", SANJIGI, false, true, JAVAJIGI);
    public static final IssueDto UPDATEDISSUE1 = new IssueDto("testSubject1", "testComment1", JAVAJIGI, false, false);

    static {
        ISSUES.add(ISSUE1);
        ISSUES.add(ISSUE2);
        ISSUES.add(ISSUE3);
        ISSUES.add(ISSUE4);
    }

    @Test
    public void create() {
        softly.assertThat(ISSUE1.getSubject()).isEqualTo("testSubject1");
        softly.assertThat(ISSUE1.getComment()).isEqualTo("testComment1");
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_no_login() {
        ISSUE1.update(null, UPDATEDISSUE1);
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_not_owner() {
        ISSUE1.update(SANJIGI, UPDATEDISSUE1);
    }

    @Test
    public void update() {
        ISSUE1.update(JAVAJIGI, UPDATEDISSUE1);
        softly.assertThat(ISSUE1.getSubject()).isEqualTo(UPDATEDISSUE1.getSubject());
        softly.assertThat(ISSUE1.getComment()).isEqualTo(UPDATEDISSUE1.getComment());
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_no_login() throws CannotDeleteException  {
        ISSUE2.delete(null);
    }

    @Test(expected = CannotDeleteException.class)
    public void delete_not_owner() throws CannotDeleteException  {
        ISSUE2.delete(SANJIGI);
    }

    @Test
    public void delete() throws CannotDeleteException {
        ISSUE2.delete(JAVAJIGI);
        softly.assertThat(ISSUE2.isDeleted()).isEqualTo(true);
    }

    @Test(expected = Exception.class)
    public void close_already_closed() throws Exception{
        ISSUE4.close(JAVAJIGI);
    }

    @Test(expected = Exception.class)
    public void close_not_assignee() throws Exception{
        ISSUE1.close(SANJIGI);
    }

    @Test
    public void close() throws Exception {
        ISSUE1.close(JAVAJIGI);
        softly.assertThat(ISSUE1.isClosed()).isEqualTo(true);
    }
}
