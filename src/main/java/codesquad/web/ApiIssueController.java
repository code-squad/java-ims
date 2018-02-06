package codesquad.web;

import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping("/{issueId}/setMilestone/{milstoneId}")
    public void registerMilestone(@PathVariable long issueId, @PathVariable long milestoneId) {
        issueService.registerMilestone(issueId, milestoneId);
    }
}

