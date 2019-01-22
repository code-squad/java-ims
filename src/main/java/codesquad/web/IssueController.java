package codesquad.web;

import codesquad.domain.user.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log = LoggerFactory.getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @Autowired
    private FileService fileService;

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
        model.addAttribute("labels", labelService.findAll());
        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id, loginUser));
        return "/issue/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, IssueDto updatedIssue) {
        issueService.update(loginUser, id, updatedIssue);
        return String.format("redirect:/issues/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        issueService.delete(loginUser, id);
        return "redirect:/";
    }

    @GetMapping("/{issueId}/setMilestone/{milestoneId}")
    public String setMilestone(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long milestoneId) {
        issueService.setMilestone(loginUser, issueId, milestoneId);
        log.debug("마일스톤 클릭!!!!!!!!!!!!!!!!!!!!!!!!!");
        return "redirect:/HissueId}";
    }

    @GetMapping("/{issueId}/setAssignee/{assigneeId}")
    public String setAssignee(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long assigneeId) {
        issueService.setAssignee(loginUser, issueId, assigneeId);
        return "redirect:/issues/{issueId}";
    }

    @GetMapping("/{issueId}/setLabel/{labelId}")
    public String setLabel(@LoginUser User loginUser, @PathVariable long issueId, @PathVariable long labelId) {
        issueService.setLabel(loginUser, issueId, labelId);
        return "redirect:/issues/{issueId}";
    }

}
