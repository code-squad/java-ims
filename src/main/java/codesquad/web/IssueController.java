package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.*;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    private static final Logger logger = getLogger(IssueController.class);

    @GetMapping
    public String issueForm(@LoginUser User loginUser) throws UnAuthorizedException {
        return "/issue/form";
    }

    @GetMapping("/{id}")
    public String issueDetail(@PathVariable Long id, Model model) {
        Issue issue = issueService.findIssue(id);
        model.addAttribute("issue", issue._toIssueDto());
        model.addAttribute("answers", issue.obtainAnswerDtos());
        return "/issue/show";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findIssue(id)._toIssueDto());
        return "/issue/updateForm";
    }
}
