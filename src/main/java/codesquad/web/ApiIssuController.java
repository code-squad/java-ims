package codesquad.web;

import codesquad.domain.Comment;
import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.CommentDto;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RequestMapping("/api/issues")
@RestController
public class ApiIssuController {
    private static final Logger log = LoggerFactory.getLogger(ApiIssuController.class);
    @Resource(name = "issueService")
    private IssueService issueService;
    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;
    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<Void> createIssue(@LoginUser User loginUser, @Valid@RequestBody IssueDto issueDto) {
        log.debug("issueDto : {}", issueDto);
        Issue savedIssue = issueService.addIssue(loginUser, issueDto.toIssue());
        log.debug("savedIssue : {}", savedIssue);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/issues/" + savedIssue.getId()));
        log.debug("headers : {}", headers);
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Issue show(@PathVariable long id) {
        return issueService.findById(id);
    }

    @PutMapping("/{id}/milestones/{milestoneId}")
    public void setMilestone(@LoginUser User loginUser, @PathVariable long id, @PathVariable long milestoneId) {
        Milestone milestone = milestoneService.findById(milestoneId);
        issueService.setMilestone(id, loginUser, milestone);
    }

    @PutMapping("/{id}/assignees/{assigneeId}")
    public void setAssignee(@LoginUser User loginUser, @PathVariable long id, @PathVariable long assigneeId) {
        User assignee = userService.findAssignee(assigneeId);
        issueService.assign(loginUser, assignee, id);
    }

    @PutMapping("/{id}/labels/{labelId}")
    public void setLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
        log.debug("Start label");
        issueService.setLabel(loginUser, id, labelId);
        log.debug("End label");
    }
}
