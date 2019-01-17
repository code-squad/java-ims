package codesquad.web_api;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Set;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    private static final Logger log = getLogger(ApiIssueController.class);

    @Resource (name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @Valid @RequestBody IssueDto issue) {
        Issue savedIssue = issueService.create(loginUser, issue);
        log.debug("saveIssue:{}", savedIssue);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/issues/" + savedIssue.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public IssueDto show(@PathVariable long id) {
        log.debug("findIssue:{}", issueService.findByIssueId(id)._toIssueDto());
        return issueService.findByIssueId(id)._toIssueDto();
    }

    @GetMapping("/{id}/milestone")
    public Iterable<Milestone> list(@PathVariable long id) {
        return milestoneService.findAll();
    }

    @GetMapping("/{id}/milestone/{milestoneId}")
    public List<Issue> milestoneChoice(@LoginUser User loginUser, @PathVariable long id, @PathVariable long milestoneId) {
        Issue issue = issueService.findByIssueId(id);
        return milestoneService.addIssue(loginUser, milestoneId, issue);
    }

    @GetMapping("/{id}/assignee/{userId}")
    public Set<User> assign(@LoginUser User loginUser , @PathVariable long id, @PathVariable long userId) {
        User assignee = userService.findById(id);
        return issueService.assignee(loginUser, id, assignee);
    }


}
