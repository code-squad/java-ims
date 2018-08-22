package codesquad.web;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    private static final Logger log = LoggerFactory.getLogger(ApiIssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/{issueId}/milestones/{milestoneId}")
    public ResponseEntity<Void> setMilestone(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long milestoneId) {
        log.debug("This is ApiIssueController");
        issueService.setMilestone(issueId, milestoneId);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    @GetMapping("/{issueId}/labels/{labelId}")
    public ResponseEntity<Void> setLabel(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long labelId) {
        issueService.setLabel(issueId, labelId);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    @GetMapping("/{issueId}/users/{assigneeId}")
    public ResponseEntity<Void> setAssignee(@LoginUser User user, @PathVariable Long issueId, @PathVariable Long assigneeId) {
        issueService.setAssignee(issueId, assigneeId);

        HttpHeaders headers = new HttpHeaders();
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
}
