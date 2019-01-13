package codesquad.web;

import codesquad.domain.issue.Issue;
import codesquad.domain.milestone.Milestone;
import codesquad.domain.user.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issue/{id}/milestone")
public class ApiMilestoneController {
    private static final Logger log = getLogger(ApiMilestoneController.class);

    @Autowired
    MilestoneService milestoneService;

    @Autowired
    IssueService issueService;

    @GetMapping("")
    public List<Milestone> list() {
        return milestoneService.findAll();
    }

    @PostMapping("/{milestoneId}")
    public ResponseEntity registerMilestone(@PathVariable long id, @PathVariable long milestoneId, @LoginUser User loginUser) {
        log.debug("이슈에 마일스톤 적용");
        issueService.registerMilestone(milestoneService.findById(milestoneId), id, loginUser);
        return new ResponseEntity("마일스톤 적용", HttpStatus.OK);
    }
}
