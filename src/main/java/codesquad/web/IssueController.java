package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
        if(!issue.getHasMilestone()) {
            model.addAttribute("milestones", milestoneService.findAllMilestone());
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
}
