package codesquad.web;

import codesquad.domain.Issue;
import codesquad.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm() {
        return "/issue/form.html";
    }

    @PostMapping()
    public String create(Issue issue) {
        issueService.save(issue);
        return "redirect:/";
    }
}
