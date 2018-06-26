package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import support.web.RestResponseEntityMaker;

@RestController
@RequestMapping("/api/issues/{id}")
public class ApiIssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private UserService userService;

    @GetMapping("/setMilestone/{milestoneId}")
    public ResponseEntity<Issue> setMilestone(@LoginUser User user, @PathVariable Long id, @PathVariable Long milestoneId) {
        Issue issue = issueService.selectMilestone(id, milestoneService.findById(milestoneId));
        return RestResponseEntityMaker.of(issue, HttpStatus.OK);
    }

    @GetMapping("/setLabel/{labelId}")
    public ResponseEntity<Issue> setLabel(@LoginUser User user, @PathVariable Long id, @PathVariable Long labelId) {
        Issue issue = issueService.selectLabel(id, Label.find(labelId));
        return RestResponseEntityMaker.of(issue, HttpStatus.OK);
    }

    @GetMapping("/setAssignee/{userId}")
    public ResponseEntity<Issue> setAssignee(@LoginUser User user, @PathVariable Long id, @PathVariable Long userId) {
        Issue issue = issueService.selectAssignee(id, userService.findById(userId));
        return RestResponseEntityMaker.of(issue, HttpStatus.OK);
    }
}
