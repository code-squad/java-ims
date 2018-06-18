package codesquad.web;

import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static support.domain.Entity.ISSUE;
import static support.domain.Entity.getMultipleEntityName;

@Controller
public class HomeController {

    @Autowired
    private IssueService issueService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(getMultipleEntityName(ISSUE), issueService.getAll());
        return "index";
    }
}
