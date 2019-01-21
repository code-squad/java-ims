package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueBody;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @Resource(name = "answerService")
    private AnswerService answerService;

    @GetMapping("/form")
    public String form (@LoginUser User loginUser) {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, @Valid IssueBody issueBody) {
        issueService.add(loginUser, issueBody);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        log.debug("### show");
        Issue issue = issueService.findById(id);
        model.addAttribute("issue", issue);
        log.debug("### issue : {}", issueService.findById(id) );
        model.addAttribute("milestones", milestoneService.findAll());

        log.debug("### users : {}", userService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("labels", labelService.findAll());

        model.addAttribute("answers", answerService.findByIssue(issue));

        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(loginUser, id));
        return "/issue/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, IssueBody updateIssueBody) {
        issueService.update(loginUser, id, updateIssueBody);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        issueService.delete(loginUser, id);
        return "redirect:/";
    }
}
