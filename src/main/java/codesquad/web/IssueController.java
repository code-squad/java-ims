package codesquad.web;

import codesquad.domain.Issue;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    private IssueService issueService;

    @GetMapping("/form")
    public String form() {
        logger.debug("Getting issue form...");
        return "/issue/form.html";
    }

    @PostMapping("")
    public String create(@RequestBody Issue issue) {
        logger.debug("Created NEW: {}", issue);
        issueService.addIssue(issue);
        return "redirect:/";
    }
}
