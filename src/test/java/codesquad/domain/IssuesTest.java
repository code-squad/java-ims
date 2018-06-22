package codesquad.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class IssuesTest {

    private List<Issue> test;

    @Before
    public void setup() {
        test = new ArrayList<>(Arrays.asList(new Issue("title1", "contents1"),
                new Issue("title2", "contents2"),
                new Issue("title3", "contents3"),
                new Issue("title4", "contents4"),
                new Issue("title5", "contents5")));
    }

    @Test
    public void numberOfOpen(){
        Issues issues = new Issues(test);
        assertThat(issues.numberOfOpen(), is(5L));
    }

    @Test
    public void numberOfOpen_2개_close(){
        Issue issue = new Issue("title2", "contents2");
        issue.writeBy(UserTest.JAVAJIGI);
        issue.setClosed(UserTest.JAVAJIGI,true);

        test.add(issue);
        test.add(issue);
        Issues issues = new Issues(test);

        assertThat(test.size(), is(7));
        assertThat(issues.numberOfOpen(), is(5L));
    }
}
