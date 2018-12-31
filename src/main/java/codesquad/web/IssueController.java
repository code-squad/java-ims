package codesquad.web;

import codesquad.domain.Issue;
import codesquad.service.IssueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issue")
public class IssueController {
    private static final Logger log = LogManager.getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm() {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(Issue newIssue) {
        issueService.create(newIssue);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        Issue issue = issueService.findById(id).get();
        model.addAttribute("issue",issue);
        return "/issue/show";
    }
}
