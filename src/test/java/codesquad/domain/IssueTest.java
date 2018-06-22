package codesquad.domain;

import codesquad.UnAuthorizedException;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class IssueTest {

    private Issue test;

    @Before
    public void setUp(){
        test = new Issue("titleTest", "contentTest");
        test.writeBy(UserTest.JAVAJIGI);
    }

    @Test
    public void update(){
        Issue update = new Issue("titleUpdate", "contentUpdate");
        test.update(UserTest.JAVAJIGI, update);
        assertThat(test.getTitle(), is("titleUpdate"));
    }

    @Test(expected = UnAuthorizedException.class)
    public void update_다른_사용자(){
        Issue update = new Issue("titleUpdate", "contentUpdate");
        test.update(UserTest.SANJIGI, update);
    }

    @Test
    public void setAssignee(){
        test.setAssignee(UserTest.SANJIGI, UserTest.JAVAJIGI);
        assertThat(test.getAssignee(), is(UserTest.SANJIGI));
    }

    @Test(expected = UnAuthorizedException.class)
    public void setAssignee_다른_사용자(){
        test.setAssignee(UserTest.SANJIGI, new User());
    }

    @Test
    public void setMilestone(){
        Milestone milestone = new Milestone("test", LocalDateTime.parse("2017-06-01T08:30"), LocalDateTime.parse("2017-06-01T08:31"));
        test.setMilestone(UserTest.JAVAJIGI, milestone);
        assertThat(test.getMilestone(), is(milestone));
    }

    @Test(expected = UnAuthorizedException.class)
    public void setMilestone_다른_사용자() {
        Milestone milestone = new Milestone("test", LocalDateTime.parse("2017-06-01T08:30"), LocalDateTime.parse("2017-06-01T08:31"));
        test.setMilestone(UserTest.SANJIGI, milestone);
    }

    @Test
    public void setLabel(){
        test.setLabel(UserTest.JAVAJIGI, Label.BUG);
        assertThat(test.getLabel(), is(Label.BUG));
    }

    @Test(expected = UnAuthorizedException.class)
    public void setLabel_다른_사용자() {
        test.setLabel(UserTest.SANJIGI, Label.BUG);
    }

    @Test
    public void close(){
        test.setClosed(UserTest.JAVAJIGI, true);
        assertThat(test.isClosed(), is(true));
    }

    @Test(expected = UnAuthorizedException.class)
    public void close_다른_사용자() {
        test.setClosed(UserTest.SANJIGI, true);
    }
}
