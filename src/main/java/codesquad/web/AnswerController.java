package codesquad.web;

import codesquad.domain.Answer;
import codesquad.domain.DeleteHistory;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("issues/{issueId}/answers")
public class AnswerController {
    private static final Logger log = getLogger(AnswerController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "answerService")
    private AnswerService answerService;

    @PostMapping("")
    public Answer add(@LoginUser User loginUser, @PathVariable long issueId, @Valid String newComment) {
        Issue issue = issueService.findById(issueId).orElseThrow(UnknownError::new);
        Answer answer = answerService.create(loginUser, issue, newComment);
        return answer;
    }

    @PutMapping("/{id}")
    public Answer update(@LoginUser User loginUser, @PathVariable long id, String update) {
        return answerService.update(loginUser, id, update);
    }

    @DeleteMapping("/{id}")
    public DeleteHistory delete(@LoginUser User user, @PathVariable long id) {
        return answerService.delete(user, id);
    }
}
