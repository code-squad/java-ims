package codesquad.web;

import codesquad.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class HomeController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("")
    public String home(Model model) {
        model.addAttribute("issues", issueService.findAll());
        return "index";
    }
}
