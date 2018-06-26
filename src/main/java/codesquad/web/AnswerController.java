package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("issue/{issueId}/answers")
public class AnswerController {
    private static final Logger log =  LoggerFactory.getLogger(AnswerController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @PostMapping("")
    public String create(@PathVariable long issueId, @LoginUser User loginUser, String contents) {
        log.info("controller parameter : {}, {}, {}", issueId, loginUser.getName(), contents);
        issueService.addAnswer(issueId, loginUser, contents);
        return String.format("redirect:/issue/%d", issueId);
    }

    @GetMapping("")
    public String list(@PathVariable long issueId) {
        log.info("answer list method called");
        issueService.list();
        return String.format("redirect:/issue/%d", issueId);
    }

    @DeleteMapping("")
    public String delete(@PathVariable long issueId, @LoginUser User loginUser, @PathVariable long answerId) throws CannotDeleteException {
        log.info("answer delete method called");
        issueService.deleteAnswer(issueId, loginUser, answerId);
        return String.format("redirect:/issue/%d", issueId);
    }
}
