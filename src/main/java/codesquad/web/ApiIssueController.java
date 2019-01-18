package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
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

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    private static final Logger log = getLogger(ApiIssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<Issue> create(@LoginUser User loginUser, @Valid @RequestBody IssueDto issueDto) {

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<Issue>(issueService.add(loginUser, issueDto) ,headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public IssueDto show(@PathVariable long id) {
        Issue issue = issueService.findById(id).orElseThrow(UnknownError::new);
        return issue._toIssueDto();
    }

    @PutMapping("/{id}")
    public IssueDto update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody IssueDto target) {
        Issue issue = issueService.update(loginUser, id, target);
        return issue._toIssueDto();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable long id) {
        try {
            issueService.deleteIssue(loginUser, id);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (CannotDeleteException e) {
            return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{id}/assignees")
    public List<User> assigneeList() {
        return userService.findAll();
    }

    @PostMapping("/{issueId}/assignees/{id}")
    public ResponseEntity<Issue> assignneAdd(@LoginUser User user, @PathVariable long issueId, @PathVariable long id) {
        return new ResponseEntity<Issue>(userService.setAssginee(issueId, id), HttpStatus.OK);
    }
}
