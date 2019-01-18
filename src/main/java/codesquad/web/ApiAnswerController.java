package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues/{issueId}/answers")
public class ApiAnswerController {
    private static final Logger log = getLogger(ApiAnswerController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "answerService")
    private AnswerService answerService;

    @PostMapping("")
    public ResponseEntity<Answer> add(@LoginUser User loginUser, @PathVariable long issueId, @Valid @RequestBody String newComment) {
        Issue issue = issueService.findById(issueId).orElseThrow(UnknownError::new);
        Answer comment = answerService.create(loginUser, issue, newComment);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return new ResponseEntity<Answer>(comment ,headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Answer updateShow(@LoginUser User loginUser, @PathVariable long id) {
        Answer answer =answerService.findById(loginUser, id);
        return answer;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Answer> update(@LoginUser User loginUser, @PathVariable long id, @RequestBody String comment) {
        log.debug("컨트롤러 확인 : {}", comment);
        return new ResponseEntity<Answer>(answerService.update(loginUser, id, comment), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void delete(@LoginUser User loginUser,@PathVariable long issueId ,@PathVariable long id) {
        answerService.delete(loginUser, id);
        log.debug("되는건가? : {}", id);
    }
}
