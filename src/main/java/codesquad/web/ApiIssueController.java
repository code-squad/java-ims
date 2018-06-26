package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Issue> setMilestone(@LoginUser User loginUser, @PathVariable long id, @PathVariable long milestoneId) {
        Issue issue = issueService.setMilestone(loginUser, id, milestoneId);
        return new ResponseEntity<>(issue, HttpStatus.CREATED);
    }

    @GetMapping("/setAssignee/{assigneeId}")
    public ResponseEntity<Issue> setAssignee(@LoginUser User loginUser, @PathVariable long id, @PathVariable long assigneeId) {
        User assignee = userService.findById(assigneeId);
        Issue issue = issueService.setAssignee(loginUser, id, assignee);
        return new ResponseEntity<>(issue, HttpStatus.CREATED);
    }

    @GetMapping("/setLabel/{labelId}")
    public ResponseEntity<Issue> setLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
        Issue issue = issueService.setLabel(loginUser, id, labelId);
        return new ResponseEntity<>(issue, HttpStatus.CREATED);
    }
}
