package codesquad.web;

import codesquad.CannotUpdateException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
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
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

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
        log.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!! :{}", milestoneService.findAll());
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
        return "redirect:/issues/{issueId}";
    }

}
