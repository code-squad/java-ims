package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.UnsupportedFormatException;
import codesquad.domain.Answer;
import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.*;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping(value = "/api/issues")
public class ApiIssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private LabelService labelService;

    @Value("${error.not.supported}")
    private String errorMessage;

    @Autowired
    private AssigneeService assigneeService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private AnswerService answerService;

    private static final Logger logger = getLogger(ApiIssueController.class);

    @PostMapping
    public ResponseEntity<Issue> createIssue(@LoginUser User loginUser, @RequestBody @Valid IssueDto issueDto, BindingResult bindingResult) {
        logger.debug("Call Method createIssue Issue Dto createIssue {}", issueDto.toString());
        if(bindingResult.hasErrors()) {
            logger.debug(errorMessage);
            return new ResponseEntity(new ErrorMessage(errorMessage)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Issue issue = issueService.createIssue(loginUser, issueDto);
        return new ResponseEntity<Issue>(issue, createHeader(String.format("/api/issues/%s", String.valueOf(issue.getId()))), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity issueDelete(@LoginUser User loginUser, @PathVariable Long id) throws UnAuthenticationException {
        logger.debug("Call Method issueDelete");
        issueService.deleteIssue(loginUser, id);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> issueUpdate(@LoginUser User loginUser, @PathVariable Long id, @RequestBody @Valid IssueDto issueDto,
                                            BindingResult bindingResult) throws UnAuthenticationException {
        if(bindingResult.hasErrors()) {
            logger.debug("Exception is Occurred Because Unsupported Data Format");
            return new ResponseEntity(new ErrorMessage(errorMessage)
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Issue issue = issueService.updateIssue(loginUser, issueDto, id);
        return new ResponseEntity<Issue>(issue, createHeader("/"), HttpStatus.OK);
    }

    @GetMapping("/{id}/updateForm")
    public String issueUpdateForm(@LoginUser User loginUser, @PathVariable Long id) throws UnAuthenticationException {
        logger.debug("Call issueUpdateForm");
        issueService.confirmOneSelf(loginUser, id);
        String location = String.format("/issues/%s/updateForm", String.valueOf(id));
        logger.debug("Location : {}" , location);
        return location;
    }

    @PostMapping("/{id}/labels/{labelId}")
    public ResponseEntity<String> registerLabel(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long labelId) throws UnAuthenticationException {
        Issue issue = issueService.findIssue(id);
        logger.debug("Call method register label, issue : {}", issue);
        labelService.registerLabel(loginUser, issue, labelId);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @PostMapping("/{id}/assignees/{assigneeId}")
    public ResponseEntity<String> registerAssignee(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long assigneeId) throws UnAuthenticationException {
        Issue issue = issueService.findIssue(id);
        logger.debug("Call registerAssignee Method(), issue : {}", issue);
        assigneeService.registerAssignee(loginUser, issue, assigneeId);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @PostMapping("/{id}/milestones/{milestoneId}")
    public ResponseEntity<String> registerMilestone(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long milestoneId) {
        Issue issue = issueService.findIssue(id);
        logger.debug("Call registerMilestone Method(), issue : {}", issue);
        milestoneService.registerMilestone(loginUser, issue, milestoneId);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    public HttpHeaders createHeader(String location) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(location));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return httpHeaders;
    }
}
