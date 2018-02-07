package codesquad.web;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping("/{issueId}/setMilestone/{milstoneId}")
    public void registerMilestone(@PathVariable long issueId, @PathVariable long milestoneId) {
        issueService.registerMilestone(issueId, milestoneId);
    }

    @PostMapping("/{id}/assignee")
    public void registerAssigee(@PathVariable long id, @LoginUser User loginUser, User assignee) {
        issueService.registerAssignee(id, loginUser, assignee);
    }
}

