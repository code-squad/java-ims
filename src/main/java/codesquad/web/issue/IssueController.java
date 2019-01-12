package codesquad.web.issue;

import codesquad.domain.issue.Issue;
import codesquad.domain.user.User;
import codesquad.domain.issue.IssueBody;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log = getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        return "issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, @Valid IssueBody issueBody, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "redirect:/issues/form";
        issueService.create(loginUser, issueBody);
        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        Issue issue = issueService.findById(loginUser, id);
        log.debug("issue updateForm :", issue);
        model.addAttribute("issue", issue);
        return "issue/updateForm";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        model.addAttribute("milestones", issueService.findMilestoneAll());
        model.addAttribute("users", issueService.findUserAll());
        model.addAttribute("labels", issueService.findLabelAll());
        return "issue/show";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, @Valid IssueBody updateIssueBody, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) return "redirect:" + String.format("/issues/%d/form", id);
        model.addAttribute("issue", issueService.update(loginUser, id, updateIssueBody));
        return "issue/show";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        issueService.deleteIssue(loginUser, id);
        return "redirect:/";
    }
}
