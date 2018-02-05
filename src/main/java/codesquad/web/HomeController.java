package codesquad.web;

import codesquad.domain.Issue;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private IssueService issueService;

    @GetMapping("/index")
    public String index() {
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Issue> issues = issueService.findAll();
        model.addAttribute("issues", issues);

        return "index";
    }
}
