package codesquad.web;

import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class HomeController {
    private static final Logger log = getLogger(HomeController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/")
    public String list(Model model) {
        model.addAttribute("issues", issueService.findAll());
        return "index";
    }
}
