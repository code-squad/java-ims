package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import support.domain.Result;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.net.URI;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues/{issueId}/answers")
public class ApiAnswerController {
    private static final Logger logger = getLogger(ApiAnswerController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "answerService")
    private AnswerService answerService;

    @PostMapping("")
    public ResponseEntity<Answer> create(@LoginUser User loginUser, @PathVariable long issueId, @RequestBody String answer) {

        logger.debug("create ## : {}", answer );
        Answer newAnswer = answerService.add(loginUser, issueId, answer);
        logger.debug("create ## : {}",  newAnswer.getFormattedCreateDate());

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(String.format("/api/issues/%d/answers/", issueId) + newAnswer.getId()));
        return new ResponseEntity<Answer>(newAnswer, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Answer show(@PathVariable long id) {
        return answerService.findById(id);
    }

    @PutMapping("/{id}")
    public Answer update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody String answer) {
        logger.debug("## update : {}",  answer);
        return answerService.update(loginUser, id, answer);
    }

    @GetMapping("/{id}/updateForm")
    public Answer updateForm(@PathVariable long id) {
        return answerService.findById(id);
    }
}
