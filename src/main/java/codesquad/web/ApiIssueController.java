package codesquad.web;

import codesquad.domain.Issue;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Void> create(@Valid @RequestBody Issue issue) {
        Issue savedIssue = issueService.add(issue);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/issues/" + savedIssue.getId()));
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
}
