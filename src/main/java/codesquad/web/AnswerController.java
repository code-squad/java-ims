package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.AnswerDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues/{id}/answers")
public class AnswerController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @PostMapping("")
    public String create(@LoginUser User loginUser, @PathVariable long id, AnswerDto answerDto) {
        issueService.addAnswer(loginUser, id, answerDto);
        return "redirect:/issues/{id}";
    }
}
