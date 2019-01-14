package codesquad.domain;


import java.util.List;

public class IssueMenus{
    private List<MenuEntity> thisIssue;

    private List<MenuEntity> others;

    public IssueMenus() {
    }

    public IssueMenus(List thisIssue, List others) {
        this.thisIssue = thisIssue;
        this.others = others;
    }

    public IssueMenus(List others) {
        this.others = others;
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
