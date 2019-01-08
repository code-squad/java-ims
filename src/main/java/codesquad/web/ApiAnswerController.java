package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.UnsupportedFormatException;
import codesquad.domain.Answer;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/api/issues/{id}/answers")
public class ApiAnswerController {

    @Value("$error.not.supported")
    private String notFormattedError;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private IssueService issueService;

    private static final Logger logger = getLogger(ApiAnswerController.class);

    @PostMapping()
    public ResponseEntity createAnswer(@LoginUser User loginUser, @PathVariable Long id, @RequestBody @Valid AnswerDto answer, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            throw new UnsupportedFormatException(notFormattedError);
        }
        Issue issue = issueService.findIssue(id);
        Answer createdAnswer = answerService.createAnswer(loginUser, issue, answer);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(String.format("/api/issues/%s/answers/%s", Long.valueOf(id)
                , Long.valueOf(createdAnswer.getId()))));
        return new ResponseEntity("success", httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{answerId}")
    public ResponseEntity updateAnswer(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long answerId,
                                       @RequestBody AnswerDto updatedAnswer) throws UnAuthenticationException {
        logger.debug("Call updateAnswer Method");
        Issue issue = issueService.findIssue(id);
        answerService.updateAnswer(loginUser, issue, updatedAnswer, answerId);
        return new ResponseEntity("success", HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity deleteAnswer(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long answerId) throws UnAuthenticationException {
        logger.debug("Call deleteAnswer Method");
        answerService.deleteAnswer(loginUser, answerId);
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
