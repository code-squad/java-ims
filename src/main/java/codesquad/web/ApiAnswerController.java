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
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import support.domain.ErrorMessage;

import javax.validation.Valid;

import java.net.URI;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues/{id}/answers")
public class ApiAnswerController {

    @Autowired
    private AnswerService answerService;

    @Autowired
    private IssueService issueService;

    private static final Logger logger = getLogger(ApiAnswerController.class);

    @PostMapping()
    public ResponseEntity createAnswer(@LoginUser User loginUser, @PathVariable Long id
            , @RequestBody @Valid AnswerDto answerDto) {
        Issue issue = issueService.findIssue(id);
        Answer createdAnswer = answerService.createAnswer(loginUser, issue, answerDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create(String.format("/api/issues/%s/answers/%s", Long.valueOf(id)
                , Long.valueOf(createdAnswer.getId()))));
        return new ResponseEntity(createdAnswer, httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{answerId}")
    public ResponseEntity updateAnswer(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long answerId,
                                       @RequestBody AnswerDto updatedAnswer) throws UnAuthenticationException {
        Issue issue = issueService.findIssue(id);
        AnswerDto answerDto = answerService.updateAnswer(loginUser, issue, updatedAnswer, answerId)._toAnswerDto();
        return new ResponseEntity(answerDto, HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity deleteAnswer(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long answerId)
            throws UnAuthenticationException {
        AnswerDto answerDto = answerService.deleteAnswer(loginUser, answerId)._toAnswerDto();
        return new ResponseEntity(answerDto, HttpStatus.OK);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerDto> updateAnswerForm(@LoginUser User loginUser, @PathVariable Long id,
                                                      @PathVariable Long answerId) throws UnAuthenticationException {
        AnswerDto answerDto = answerService.detailAnswer(loginUser, answerId);
        return new ResponseEntity(answerDto, HttpStatus.OK);
    }
}
