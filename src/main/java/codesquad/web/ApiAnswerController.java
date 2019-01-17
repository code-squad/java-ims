package codesquad.web;

import codesquad.domain.issue.Issue;
import codesquad.domain.issue.answer.Answer;
import codesquad.domain.user.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        Answer ret = answerService.add(loginUser, issue, comment);
        return ret;
    }
}
