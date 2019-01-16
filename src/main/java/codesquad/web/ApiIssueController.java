package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.UnsupportedFormatException;
import codesquad.domain.*;
import codesquad.dto.AnswerDto;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import support.domain.ErrorMessage;

import javax.validation.Valid;

import java.net.URI;
import java.util.Arrays;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/api/issues")
public class ApiIssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private MessageSource messageSource;

    private static final Logger logger = getLogger(ApiIssueController.class);

    @PostMapping
    public ResponseEntity<Issue> createIssue(@LoginUser User loginUser, @RequestBody @Valid IssueDto issueDto) {
        Issue issue = issueService.createIssue(loginUser, issueDto);
        return new ResponseEntity<Issue>(issue, createHeader(String.format("/api/issues/%s", String.valueOf(issue.getId()))), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity issueDelete(@LoginUser User loginUser, @PathVariable Long id) throws UnAuthenticationException {
        issueService.deleteIssue(loginUser, id);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> issueUpdate(@LoginUser User loginUser, @PathVariable Long id, @RequestBody @Valid IssueDto issueDto) throws UnAuthenticationException {
        Issue issue = issueService.updateIssue(loginUser, issueDto, id);
        return new ResponseEntity<Issue>(issue, createHeader("/"), HttpStatus.OK);
    }

    @GetMapping("/{id}/updateForm")
    public String issueUpdateForm(@LoginUser User loginUser, @PathVariable Long id) throws UnAuthenticationException {
        issueService.confirmOneSelf(loginUser, id);
        return String.format("/issues/%s/updateForm", String.valueOf(id));
    }

    public HttpHeaders createHeader(String location) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(location));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return httpHeaders;
    }
}
