package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.net.URI;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("")
public class ApiMilestoneController {
    private static final Logger log = getLogger(ApiMilestoneController.class);

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @PostMapping("/api/milestones")
    public ResponseEntity<Milestone> create(@LoginUser User user, @Valid @RequestBody Milestone milestone) {
        Milestone newMilestone = milestoneService.create(user, milestone);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.valueOf(newMilestone.getId())));
        return new ResponseEntity<Milestone>(newMilestone, headers, HttpStatus.CREATED);
    }

    @GetMapping("/api/issues/{{issueId}}/milestones")
    public List<Milestone> list() {
        log.debug("asdfasdfasd: {}", milestoneService.findAll());
        return milestoneService.findAll();
    }

    @PostMapping("/api/issues/{issueId}/milestones/{id}")
    public ResponseEntity<Issue> add(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long id) {
        return new ResponseEntity<Issue>( milestoneService
                .setMilestone(loginUser, issueId, id), HttpStatus.OK);
    }
}
