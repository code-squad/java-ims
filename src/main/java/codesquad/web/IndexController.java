package codesquad.web;

import codesquad.domain.Issue;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private IssueService issueService;

    @GetMapping("/")
    public String show(Model model) {
        List<Issue> issueList = issueService.findAllIssues();
        model.addAttribute("issues", issueList);

        return "index";
    }
}
