package codesquad.web;

import codesquad.service.IssueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class HomeController {
    private static final Logger log = LogManager.getLogger(HomeController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/")
    public String home(Model model) {
        log.info("home info 여기는 홈입니다.");
        model.addAttribute("issues", issueService.findAll());
        return "home";
    }
}
