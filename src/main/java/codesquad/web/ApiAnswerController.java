package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;

@RestController
@RequestMapping("/api/issues/{issueId}/answers")
public class ApiAnswerController {

    private final Logger log = LoggerFactory.getLogger(ApiAnswerController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "answerService")
    private AnswerService answerService;

    @PostMapping("")
    public ResponseEntity<Void> create(@LoginUser User loginUser, @PathVariable Long issueId, @RequestBody String content) {
        Answer savedAnswer = issueService.addAnswer(loginUser, issueId, content);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/api/issues/%d/answers/%d", issueId, savedAnswer.getId())));
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @GetMapping("/{answerId}")
    public ResponseEntity<Answer> read(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long answerId) {
        return ResponseEntity.ok(answerService.findAnswerById(answerId));
    }

    @PutMapping("{answerId}")
    public ResponseEntity<Answer> update(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long answerId, @RequestBody String content) throws UnAuthenticationException {
        Answer updateAnswer = answerService.update(loginUser, answerId, content);
        log.debug("updated answer : {}", updateAnswer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(updateAnswer);
    }

    @DeleteMapping("{answerId}")
    public ResponseEntity<Void> delete(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long answerId) throws UnAuthenticationException {
        answerService.delete(loginUser, answerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
