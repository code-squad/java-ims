package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import support.domain.UriGeneratable;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    private IssueService issueService;

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        logger.debug("Getting issue form...");
        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, Issue newIssue) {
        logger.debug("Created NEW: {}", newIssue);
        newIssue.setWriter(loginUser);
        UriGeneratable issue = issueService.addIssue(newIssue);
        return "redirect:" + issue.generateUri();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        Issue issue = issueService.findById(id);
        logger.debug("Showing Question... {}", issue);
        model.addAttribute("issue", issue);
        return "/issue/show";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, IssueDto issueDto) {
        UriGeneratable issue = issueService.updateIssue(loginUser, id, issueDto);
        logger.debug("Issue updated...! ");
        return "redirect:" + issue.generateUri();
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        issueService.deleteIssue(loginUser, id);
        logger.debug("Issue deleted...!");
        return "redirect:/";
    }
}
