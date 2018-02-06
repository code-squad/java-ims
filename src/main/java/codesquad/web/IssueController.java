package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
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
    public String createForm(@LoginUser User user) {
        return "/issue/form";
    }

    @GetMapping("")
    public String showList() {
        return "redirect:/";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto issueDto) {
        Issue issue = issueService.add(loginUser, issueDto);
        return String.format("redirect:/issues/%d", issue.getId());
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        model.addAttribute("milestones", milestoneService.findAll());
        model.addAttribute("users", userService.findAll());
        return "/issue/show";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable Long id, IssueDto issueDto) {
        issueService.update(loginUser, id, issueDto);

        return String.format("redirect:/issues/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable Long id) {
        issueService.delete(loginUser, id);

        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) {
        try {
            model.addAttribute("issue", issueService.findByIdForEdit(loginUser, id));
            return "/issue/updateForm";
        } catch (UnAuthorizedException e) {
            model.addAttribute("error", e.getMessage());
            return "/user/login";
        }
    }

    @PutMapping("/{issueId}/setMilestone/{milestoneId}")
    public String setMilestone(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long milestoneId) {
        issueService.setMilestone(loginUser, issueId, milestoneId);

        return String.format("redirect:/issues/%d", issueId);
    }
}
