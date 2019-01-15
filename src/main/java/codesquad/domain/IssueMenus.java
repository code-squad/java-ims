package codesquad.domain;


import java.util.ArrayList;
import java.util.List;

public class IssueMenus{
    private long issueId;

    private List<MenuEntity> thisIssue = new ArrayList<>();

    private List<MenuEntity> others;

    public IssueMenus() {
    }

    public IssueMenus(List thisIssue, List others) {
        this.thisIssue = thisIssue;
        this.others = others;
    }

    public IssueMenus(long id,List others) {
        /*this.others = others;
        for (MenuEntity other : this.others) {
            if (other.getId() == id) {
                thisIssue.add(other);
                others.remove(other);
            }
        }*/
        this.issueId = id;
        this.others = others;
    }

    public IssueMenus(List others) {
        this.others = others;
    }

    public long getIssueId() {
        return issueId;
    }

    public void setIssueId(long issueId) {
        this.issueId = issueId;
    }

    public List getThisIssue() {
        return thisIssue;
    }

    public void setThisIssue(List thisIssue) {
        this.thisIssue = thisIssue;
    }

    public List getOthers() {
        return others;
    }

    public void setOthers(List others) {
        this.others = others;
    }


}
