package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issues/{id}")
public class ApiIssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/setMilestone/{milestoneId}")
    public Issue setMilestone(@LoginUser User loginUser, @PathVariable long id, @PathVariable long milestoneId) {
        return issueService.setMilestone(loginUser, id, milestoneId);
    }

    @GetMapping("/setAssignee/{assigneeId}")
    public Issue setAssignee(@LoginUser User loginUser, @PathVariable long id, @PathVariable long assigneeId) {
        User assignee = userService.findById(assigneeId);
        return issueService.setAssignee(loginUser, id, assignee);
    }

    @GetMapping("/setLabel/{labelId}")
    public Issue setLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
        return issueService.setLabel(loginUser, id, labelId);
    }
}
