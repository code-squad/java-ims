package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AssigneeService;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues/{id}/assignees")
public class ApiAssigneeController {

    private static final Logger logger = getLogger(ApiAssigneeController.class);

    @Autowired
    private IssueService issueService;

    @Autowired
    private AssigneeService assigneeService;

    @Autowired
    private UserService userService;

    @PostMapping("/{assigneeId}")
    public ResponseEntity<String> registerAssignee(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long assigneeId) throws UnAuthenticationException {
        Issue issue = issueService.findIssue(id);
        logger.debug("Call registerAssignee Method(), issue : {}", issue);
        assigneeService.registerAssignee(loginUser, issue, assigneeId);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @GetMapping()
    public List<User> list(@PathVariable Long id) {
        List<User> assignees = new ArrayList<>();
        if(issueService.findIssue(id).isAssignee()) {
            assignees.add(issueService.findIssue(id).getWriter());
        }

        if(!issueService.findIssue(id).isAssignee()) {
            assignees = userService.findAll();
        }

        return assignees;
    }
}
