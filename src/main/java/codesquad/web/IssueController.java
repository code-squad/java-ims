package codesquad.web;

import codesquad.domain.*;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @GetMapping("")
    public String createForm(@LoginUser User loginUser) {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto newIssue) {
        log.debug("loginUser:{}", loginUser);
        issueService.create(loginUser, newIssue);
        return "redirect:/";
    }

    @GetMapping("/{id}")        //홈 화면에서 이슈제목을 눌렀을때 (마일스톤 누를때 동적으로 이슈 게시판에 어떤 작성자가 어떤 마일스톤을 누르는지에 대한 구현 포기..)
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findByIssueId(id));
        model.addAttribute("milestones", milestoneService.findAll());
        model.addAttribute("allLabels", labelService.findAll());
        model.addAttribute("users", userService.findAll());
        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    public String modifyForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        Issue issue = issueService.findByIssueId(id);
        if (issue.isOwner(loginUser)) {
            model.addAttribute("issue", issue._toIssueDto());
            return "/issue/updateForm";
        }
        return String.format("redirect:/issues/%d", id);
    }

    @PutMapping("/{id}")
    public String modify(@LoginUser User loginUser, @PathVariable long id, IssueDto updateIssue) {
        issueService.update(loginUser, id, updateIssue);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUSer, @PathVariable long id) {
        issueService.deleteIssue(loginUSer, id);
        return "redirect:/";
    }

    @GetMapping("/{id}/close")
    public String close(@LoginUser User loginUser, @PathVariable long id) {
        issueService.close(loginUser, id);
        return "redirect:/issues/{id}";
    }

    @GetMapping("/{id}/open")
    public String open(@LoginUser User loginUser, @PathVariable long id) {
        issueService.open(loginUser, id);
        return "redirect:/issues/{id}";
    }

    @GetMapping("/{id}/milestone/{milestoneId}")
    public String milestoneChoice(@LoginUser User loginUser, @PathVariable long id, @PathVariable long milestoneId) {
        Issue issue = issueService.findByIssueId(id);
        milestoneService.addIssue(loginUser,milestoneId, issue);
        return "redirect:/issues/{id}";
    }

    @GetMapping("/{id}/label/{labelId}")
    public String labelChoice(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
        Label label = labelService.findByLabelId(labelId);
        issueService.addLabel(loginUser, id , label);
        return "redirect:/issues/{id}";
    }

    @GetMapping("/{id}/assignee/{userId}")
    public String assign(@LoginUser User loginUser, @PathVariable long id, @PathVariable long userId) {
        User assignee = userService.findById(userId);
        issueService.assignee(loginUser, id, assignee);
        return "redirect:/issues/{id}";
    }


}
