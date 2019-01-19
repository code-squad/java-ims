package codesquad.web;

import codesquad.domain.issue.Issue;
import codesquad.domain.issue.answer.Answer;
import codesquad.domain.user.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import support.domain.Result;

import static org.slf4j.LoggerFactory.getLogger;

@RestController
@RequestMapping("/api/issues/{id}/answers")
public class ApiAnswerController {
    private static final Logger log = getLogger(ApiAnswerController.class);

    @Autowired
    private AnswerService answerService;

    @Autowired
    private IssueService issueService;

    @PostMapping("")
    public Answer create(@LoginUser User loginUser, @PathVariable long id, String comment) {
        Issue issue = issueService.findById(id);
        return answerService.add(loginUser, issue, comment);
    }

    @DeleteMapping("/{answerId}")
    public Result delete(@LoginUser User loginUser, @PathVariable long id, @PathVariable long answerId) {
        try {
            answerService.delete(answerId, loginUser);
            return Result.ok();
        }catch (Exception e) {
            return Result.error("삭제할 수 없습니다.");
        }
    }
}
