package codesquad.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class IssueMenusTest {

    @Test
    public void list() {
        List<Milestone> milestones = new ArrayList<>();
        milestones.add(new Milestone());
        milestones.add(new Milestone());
        milestones.add(new Milestone());
        System.out.println(milestones.size());
        milestones.get(1);
        milestones.remove(1);
        System.out.println(milestones.size());
        IssueMenus issueMenus = new IssueMenus();
    }

}