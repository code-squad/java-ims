package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
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

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @Resource(name = "commentService")
    private CommentService commentService;

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        log.debug("issue form");
        return "/issue/form";
    }

    @PostMapping()
    public String create(@LoginUser User user, Issue issue) {
        log.debug("issue : {}", issue.toString());
        issueService.save(issue);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) throws CannotShowException {
        model.addAttribute("issue", issueService.findById(id));
        model.addAttribute("milestones", milestoneService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("labels", labelService.findAll());
        model.addAttribute("comments", commentService.findAllByIssueId(id));
        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    String updateForm(@PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        return "/issue/updateForm";
    }

    @PostMapping("/{id}/form")
    public String update(@PathVariable long id, Issue updateIssue) {
        issueService.update(id, updateIssue);
        return String.format("/issues/%d", id);
    }
}
