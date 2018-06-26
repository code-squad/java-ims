package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
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

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private UserService userService;

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
        model.addAttribute("issue", issueService.findById(id));
        model.addAttribute("milestones", milestoneService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("labels", Label.values());
        return "/issue/show";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@LoginUser User user, @PathVariable long id, Model model) {
        Issue target = issueService.findById(id);
        model.addAttribute("issue", target);
        return "/issue/updateForm";
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

    @GetMapping("/{id}/setAssignee/{userId}")
    public String setAssignee(@LoginUser User loginUser, @PathVariable long id, @PathVariable long userId) {
        UriGeneratable issue = issueService.setAssignee(loginUser, id, userService.findById(userId));
        return "redirect:" + issue.generateUri();
    }

    @GetMapping("/{id}/setLabel/{labelId}")
    public String setLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
        UriGeneratable issue = issueService.setLabel(loginUser, id, labelId);
        return "redirect:" + issue.generateUri();
    }

    @GetMapping("/{id}/setMilestone/{milestoneId}")
    public String setMilestone(@LoginUser User loginUser, @PathVariable long id, @PathVariable long milestoneId) {
        UriGeneratable issue = issueService.setMilestone(loginUser, id, milestoneId);
        return "redirect:/" + issue.generateUri();
    }
}
