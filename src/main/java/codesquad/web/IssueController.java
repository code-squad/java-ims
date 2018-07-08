package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Label;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log = LoggerFactory.getLogger(IssueController.class);

    @Resource(name = "issueService")
    IssueService issueService;

    @Resource(name = "milestoneService")
    MilestoneService milestoneService;

    @Resource(name = "userService")
    UserService userService;

    @Resource(name = "labelService")
    LabelService labelService;

    @GetMapping("/form")
    public String createForm(@LoginUser User loginUser) {
        return "/issue/form";
    }

    @PostMapping
    public String create(@LoginUser User loginUser, IssueDto issue) {
        Issue returnedIssue = issueService.add(issue.toIssue(), loginUser);
        return "redirect:/issues/" + returnedIssue.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Issue issue = issueService.get(id);
        model.addAttribute("issue", issue);
        log.debug("id : {}, issue : {}", id, issue);

        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable Long id, Model model) {
        Issue issue = issueService.get(id);
        model.addAttribute("issue", issue);

        return "/issue/updateForm";
    }

    // TODO VALID?
    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable Long id, IssueDto target) {
        issueService.update(loginUser, id, target);
        return String.format("redirect:/issues/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable Long id) {
        issueService.delete(loginUser, id);
        return "redirect:/";
    }

    @PostMapping("/{issueId}/setMilestone/{milestoneId}")
    public String setMilestone(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long milestoneId) {
        Milestone milestone = milestoneService.getMilestone(milestoneId);
        Issue issue = issueService.setMilestone(issueId, milestone);

        log.debug("issue : {}", issue);

        return String.format("redirect:/issues/%d", issueId);
    }

    @PostMapping("/{issueId}/setAssignee/{userId}")
    public String setAssignee(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long userId) {
        User user = userService.getUser(userId);
        Issue issue = issueService.setAssignee(issueId, user);

        log.debug("issue : {}", issue);

        return String.format("redirect:/issues/%d", issueId);
    }

    @PostMapping("/{issueId}/setLabel/{labelId}")
    public String setLabel(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long labelId) {
        Label label = labelService.getLabel(labelId);
        Issue issue = issueService.setLabel(issueId, label);

        log.debug("issue : {}", issue);

        return String.format("redirect:/issues/%d", issueId);
    }
}
