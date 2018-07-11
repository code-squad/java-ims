package codesquad.web;

import codesquad.domain.Issue;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log =  LoggerFactory.getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm() {
        return "/issue/form.html";
    }

    @PostMapping()
    public String create(Issue issue) {
        log.debug("issue : {}", issue.toString());
        issueService.save(issue);
        return "redirect:/";
    }
}
