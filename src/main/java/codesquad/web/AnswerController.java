package codesquad.web;

import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("issues/{issueId}/answers")
public class AnswerController {
    @Resource(name = "answerService")
    private AnswerService answerService;

    @PostMapping("")
    public String create(@PathVariable long issueId, @LoginUser User loginUser, String contents) {
        answerService.add(issueId, loginUser, contents);
    }
}
