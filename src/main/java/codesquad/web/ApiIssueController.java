package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    private static final Logger log = getLogger(ApiIssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @Valid @RequestBody IssueDto issueDto) {
        Issue issue = issueService.add(loginUser, issueDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/"));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public IssueDto show(@PathVariable long id) {
        Issue issue = issueService.findById(id).orElseThrow(UnAuthorizedException::new);
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
}
