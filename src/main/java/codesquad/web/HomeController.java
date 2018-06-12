package codesquad.web;

import codesquad.domain.Issue;
import codesquad.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class HomeController {

    @Resource(name = "issueService")
    IssueService issueService;

    @GetMapping("")
    public String list(Model model) {
        List<Issue> issues = issueService.findAll();
        model.addAttribute("issues", issues);
        return "/index";
    }
}
