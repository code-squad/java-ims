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

    @Value("${error.not.supported}")
    private String notFormattedError;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private IssueService issueService;

    private static final Logger logger = getLogger(ApiAnswerController.class);

    @PostMapping()
    public ResponseEntity createAnswer(@LoginUser User loginUser, @PathVariable Long id, @RequestBody AnswerDto answerDto) {
        logger.debug("Call createAnswer Method, AnswerDto : " + answerDto.toString());
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
        logger.debug("Call updateAnswer Method");
        Issue issue = issueService.findIssue(id);
        AnswerDto answerDto = answerService.updateAnswer(loginUser, issue, updatedAnswer, answerId)._toAnswerDto();
        return new ResponseEntity(answerDto, HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity deleteAnswer(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long answerId) throws UnAuthenticationException {
        logger.debug("Call deleteAnswer Method");
        AnswerDto answerDto = answerService.deleteAnswer(loginUser, answerId)._toAnswerDto();
        return new ResponseEntity(answerDto, HttpStatus.OK);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<AnswerDto> updateAnswerForm(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long answerId) throws UnAuthenticationException {
        logger.debug("Call detailAnswer Method");
        AnswerDto answerDto = answerService.detailAnswer(loginUser, answerId);
        return new ResponseEntity(answerDto, HttpStatus.OK);
    }
}
