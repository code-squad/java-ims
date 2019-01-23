package codesquad.web;

import codesquad.domain.issue.Issue;
import codesquad.service.IssueService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class HomeController {

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/")
    public String home(Model model, Pageable pageable) {
        Iterable<Issue> issues = issueService.findAll();
        model.addAttribute("issues", issues);
        return "index";
    }
}
