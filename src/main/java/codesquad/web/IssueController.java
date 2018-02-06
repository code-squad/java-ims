package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private static final Logger log = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    IssueService issueService;

    @PostMapping("")
    public String createIssue(@LoginUser User loginUser, String title, String comment) {
        IssueDto issue = new IssueDto()
                .setTitle(title)
                .setComment(comment)
                .setAuthor(loginUser);
        issueService.createIssue(issue);

        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable long id, Model model) throws Exception {
        Optional<Issue> issueOptional = issueService.findIssueById(id);
        issueOptional.ifPresent(i -> model.addAttribute("issue", i));

        return "/issue/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, @LoginUser User loginUser) throws Exception {
        issueService.delete(id, loginUser);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, @LoginUser User loginUser, String title, String comment) throws Exception {
        issueService.update(id, loginUser, new IssueDto().setTitle(title).setComment(comment) );
        return "redirect:/";
    }
}
