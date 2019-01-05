package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.*;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @Autowired
    private AssigneeService assigneeService;

    @Autowired
    private LabelService labelService;

    private static final Logger logger = getLogger(IssueController.class);

    @GetMapping
    public String issueForm(@LoginUser User loginUser) throws UnAuthorizedException {
        logger.debug("Call issueForm Method()");
        return "/issue/form";
    }

    @GetMapping("/{id}")
    public String issueDetail(@PathVariable Long id, Model model) {
        Issue issue = issueService.findIssue(id);
        model.addAttribute("issue", issue._toIssueDto());
        model.addAttribute("issueId", id);

        if(!issue.isMilestone()) {
            model.addAttribute("milestones", milestoneService.findAllMilestone());
        }

        if(!issue.isAssignee()) {
            model.addAttribute("assignees", userService.findAll());
        }

        if(!issue.isLabel()) {
            model.addAttribute("labels", labelService.findAll());
        }
        return "/issue/show";
    }

    @GetMapping("/{id}/updateForm")
    public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findIssue(id)._toIssueDto());
        model.addAttribute("id", id);
        return "/issue/updateForm";
    }

    @GetMapping("/{id}/milestones/{milestoneId}")
    public String registerMilestone(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long milestoneId) {
        Issue issue = issueService.findIssue(id);
        logger.debug("Call registerMilestone Method(), issue : {}", issue);
        milestoneService.registerMilestone(loginUser, issue, milestoneId);
        return "redirect:/issues/" + Long.valueOf(id);
    }

    @GetMapping("/{id}/assignees/{assigneeId}")
    public String registerAssignee(@LoginUser User loginUser, @PathVariable Long id, @PathVariable Long assigneeId) {
        Issue issue = issueService.findIssue(id);
        logger.debug("Call registerAssignee Method(), issue : {}", issue);
        assigneeService.registerAssignee(loginUser, issue, assigneeId);
        return "redirect:/issues/" + Long.valueOf(id);
    }
}
