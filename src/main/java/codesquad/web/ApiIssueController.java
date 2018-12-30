package codesquad.web;

import codesquad.domain.Issue;
import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import support.domain.ErrorMessage;

import javax.validation.Valid;

import java.net.URI;
import java.util.Arrays;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {

    @Autowired
    private IssueService issueService;

    private static final Logger logger = getLogger(ApiIssueController.class);

    @PostMapping
    public ResponseEntity<Issue> createIssue(@Valid IssueDto issueDto, BindingResult bindingResult) {
        logger.debug("Call Method createIssue Issue Dto createIssue {}", issueDto.toString());
        if(bindingResult.hasErrors()) {
            logger.debug("Exception is Occurred Because Unsupported Data Format");
            return new ResponseEntity(new ErrorMessage("Exception is Occurred Because Unsupported Data Format")
                    , HttpStatus.INTERNAL_SERVER_ERROR);
        }
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/"));
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
        return new ResponseEntity<Issue>(issueService.createIssue(issueDto), httpHeaders, HttpStatus.CREATED);
    }
}
