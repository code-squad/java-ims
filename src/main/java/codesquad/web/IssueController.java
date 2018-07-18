package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log =  LoggerFactory.getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        log.debug("issue form");
        return "/issue/form";
    }

    @PostMapping()
    public String create(@LoginUser User user, Issue issue) {
        log.debug("issue : {}", issue.toString());
        issueService.save(issue);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
         model.addAttribute("issue", issueService.findById(id));
        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        return "/issue/updateForm";
    }

    @PostMapping("/{id}/form")
    public String update(@PathVariable long id, Issue updateIssue) {
        issueService.update(id, updateIssue);
        return String.format("/issues/%d", id);
    }
}
