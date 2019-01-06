package codesquad.web;

import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private IssueService issueService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("issues", issueService.findAll());
        return "/index";
    }
}
