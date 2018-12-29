package codesquad.web;

import codesquad.service.IssueService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/issue")
public class IssueController {
    private static final Logger log = LogManager.getLogger(IssueController.class);

    private IssueService issueService;

    @GetMapping("/form")
    public String createForm() {

        return "/issue/form";
    }

    @PostMapping("")
    public String create() {
        return "redirect:/";
    }
}
