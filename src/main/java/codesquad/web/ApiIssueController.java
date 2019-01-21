package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueBody;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import com.sun.tools.javac.main.Main;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import support.domain.Result;

import javax.annotation.Resource;
import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    private static final Logger log = getLogger(ApiIssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "answerService")
    private AnswerService answerService;

    @GetMapping("/{issuesId}/milestones/{milestonesId}")
    public Result addMilestone( @PathVariable long issuesId,
                              @PathVariable long milestonesId) {
        log.debug("issueId : {}", issuesId);
        log.debug("milestonesid  : {}", milestonesId);
        issueService.addMilestone(issuesId, milestonesId);

        Milestone milestone = milestoneService.findById(milestonesId);

        return Result.ok(milestone);
    }

    @GetMapping("/{issuesId}/users/{userId}")
    public Result addAssignee(@PathVariable long issuesId, @PathVariable long userId) {
        issueService.addAssignee(issuesId, userId);
        return Result.ok();
    }

    @GetMapping("/{issuesId}/labels/{lableId}")
    public Result addLables(@PathVariable long issuesId, @PathVariable long lableId) {
        issueService.addLables(issuesId, lableId);
        return Result.ok();
    }

    @PostMapping("")
    public ResponseEntity<Issue> create(@LoginUser User loginUser, @RequestBody IssueBody issueBody) {
        Issue issue = issueService.add(loginUser, issueBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/issues/" + issue.getId()));
        return new ResponseEntity<Issue>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Issue show(@PathVariable long id) {
        return issueService.findById(id);
    }


}
