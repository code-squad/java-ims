package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    IssueService issueService;

    @PostMapping("")
    public String createIssue(IssueDto issue) {
        issueService.createIssue(issue);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable long id, @LoginUser User loginUser, Model model) throws Exception {
        Optional<Issue> issueOptional = issueService.findIssueById(id);
        model.addAttribute("issue", issueOptional.orElse(null));

        return "/issue/show";
    }
}
