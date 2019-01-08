package codesquad.web;

import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class MainController {

    private static final Logger logger = getLogger(MainController.class);

    @Autowired
    private IssueService issueService;

    @GetMapping("/")
    public String home(Model model, HttpSession httpSession) {
        logger.debug("Call Method home()");
        model.addAttribute("issues", issueService.findAllIssue());
        return "index";
    }
}
