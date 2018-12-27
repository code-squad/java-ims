package codesquad.web;

import codesquad.domain.Issue;
import codesquad.service.IssueService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class HomeController {

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/")
    public String home(Model model, Pageable pageable) {
        List<Issue> issues = issueService.findAll(pageable);
        model.addAttribute("issues", issues);
        return "index";
    }
}
