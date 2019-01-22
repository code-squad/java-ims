package codesquad.domain;


import java.util.ArrayList;
import java.util.List;

public class IssueMenus {
    private long issueId;

    private List thisIssue = new ArrayList<>();

    private List others;

    public IssueMenus() {
    }

    public IssueMenus(long id, Object owner, List others) {
        this.others = others;
        if (owner != null) {
            this.thisIssue.add(owner);
            this.others.remove(owner);
        }
        this.issueId = id;
    }

    public IssueMenus(long id, List owners, List others) {
        this.others = others;
        if (owners != null) {
            for (Object owner : owners) {
                this.thisIssue.add(owner);
                this.others.remove(owner);
            }
        }
        this.issueId = id;
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
