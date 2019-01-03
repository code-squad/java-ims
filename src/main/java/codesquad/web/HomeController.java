package codesquad.web;

import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class HomeController {
    private static final Logger log = LoggerFactory.getLogger(HomeController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/")
    public String home(Model model) {
        log.debug("Home!!!!!!!!!!!!");
        model.addAttribute("issues", issueService.findAll());
        return "/index";
    }
}
