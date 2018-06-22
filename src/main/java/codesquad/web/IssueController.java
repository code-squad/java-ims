package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.exception.AlreadyAssignException;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

@RequestMapping("/issues")
@Controller
public class IssueController {

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @GetMapping("/form")
    public String createForm(@LoginUser User loginUser) {
        return "issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto issueDto) {
        Issue newIssue = issueService.save(loginUser, issueDto);
        return "redirect:/issues/" + newIssue.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        model.addAttribute("users", userService.findAll());
        model.addAttribute("milestones", milestoneService.findAll());
        return "issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) throws UnAuthenticationException {
        model.addAttribute("issue", issueService.findById(loginUser, id));
        return "issue/updateForm";
    }

    @PutMapping("/{id}")
    public String updateIssue(@LoginUser User loginUser, @PathVariable Long id, IssueDto target) throws UnAuthenticationException {
        Issue issue = issueService.update(loginUser, id, target);
        return String.format("redirect:%s", issue.generateUrl());
    }

    @DeleteMapping("/{id}")
    public String deleteIssue(@LoginUser User loginUser, @PathVariable Long id) throws UnAuthenticationException {
        issueService.delete(loginUser, id);
        return "redirect:/";
    }

    @GetMapping("/{issueId}/setMilestone/{milestoneId}")
    public String setMilestone(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long milestoneId) throws AlreadyAssignException {
        issueService.setMilestone(issueId, milestoneService.findById(milestoneId));
        milestoneService.setIssue(milestoneId, issueService.findById(issueId));
        return "redirect:/issues/" + issueId;
    }

    @GetMapping("/{issueId}/setAssignee/{assigneeId}")
    public String setAssignee(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long assigneeId) {
        issueService.setAssignee(issueId, userService.findById(assigneeId));
        return "redirect:/issues/" + issueId;
    }



}

