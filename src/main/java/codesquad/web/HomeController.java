package codesquad.web;

import codesquad.domain.issue.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
//    @Autowired
//    IssueService issueService;

    @Autowired
    IssueRepository issueRepository;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("issue", issueRepository.findByDeleted(false));
        return "index";
    }
}
