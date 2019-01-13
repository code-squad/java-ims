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

import java.net.URI;

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

    @PutMapping("/{id}")
    public Answer update(@LoginUser User loginUser, @PathVariable long id, @Valid @RequestBody String update) {
        return answerService.update(loginUser, id, update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@LoginUser User loginUser,@PathVariable long issueId ,@PathVariable long id) {
        answerService.delete(loginUser, id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/api/issues/" + issueId + "/answers"));
        log.debug("되는건가? : {}", id);
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
}
