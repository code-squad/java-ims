package codesquad.web.api;

import codesquad.domain.issue.Issue;
import codesquad.domain.user.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @PostMapping("")
    public ResponseEntity<Issue> create(@LoginUser User loginUser, @Valid @RequestBody IssueDto issueDto) {
        issueService.add(loginUser, issueDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<Issue>(headers, HttpStatus.FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> show(@PathVariable long id) {
        Issue currentIssue = issueService.findById(id);
        return new ResponseEntity<Issue>(currentIssue, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody IssueDto updatedIssueDto) {
        issueService.update(loginUser, id, updatedIssueDto);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/api/issues/%d", id)));
        return new ResponseEntity<Issue>(headers, HttpStatus.FOUND);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Issue> delete(@LoginUser User loginUser, @PathVariable long id) {
        issueService.delete(loginUser, id);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<Issue>(headers, HttpStatus.FOUND);
    }
}
