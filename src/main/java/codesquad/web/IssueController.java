package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto issueDto) {
        issueService.add(loginUser, issueDto);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        model.addAttribute("milestones", milestoneService.findAll());
        model.addAttribute("assignees", userService.findAll());
        model.addAttribute("allLabels", labelService.findAll());

        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        try {
            model.addAttribute("issue", issueService.findById(loginUser, id));
            return "/issue/updateForm";
        } catch (UnAuthorizedException e) {
            return "redirect:/issues/{id}";
        }
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, IssueDto updatedIssue) {
        issueService.update(loginUser, id, updatedIssue);
        return "redirect:/issues/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        try {
            issueService.delete(loginUser, id);
            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/issues/{id}";
        }
    }

    @GetMapping("/{issueId}/milestones/{milestoneId}")
    public String setMilestone(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long milestoneId, RedirectAttributes redirectAttrs) {
        try {
            issueService.setMilestone(loginUser, issueId, milestoneId);
            return "redirect:/issues/{issueId}";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/issues/{issueId}";
        }
    }

    @GetMapping("/{id}/closed")
    public String close(@LoginUser User loginUser, @PathVariable long id, RedirectAttributes redirectAttrs) {
        try {
            issueService.close(loginUser, id);
            return "redirect:/";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/issues/{id}";
        }
    }

    @GetMapping("/{issueId}/assignees/{assigneeId}")
    public String setAssignee(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long assigneeId, RedirectAttributes redirectAttrs) {
        try {
            issueService.setAssignee(loginUser, issueId, assigneeId);
            return "redirect:/issues/{issueId}";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/issues/{issueId}";
        }
    }

    @GetMapping("/{issueId}/labels/{labelId}")
    public String setLabel(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long labelId, RedirectAttributes redirectAttrs) {
        try {
            issueService.setLabel(loginUser, issueId, labelId);
            return "redirect:/issues/{issueId}";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/issues/{issueId}";
        }
    }
}
