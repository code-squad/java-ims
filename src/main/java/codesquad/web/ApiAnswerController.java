package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.net.URI;

@RestController
@RequestMapping("/api/issues/{issueId}/answers")
public class ApiAnswerController {

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
    public Answer read(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long answerId) {
        return answerService.findAnswerById(answerId);
    }
}
