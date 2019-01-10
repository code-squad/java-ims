package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.*;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource(name = "labelRepository")
    private LabelRepository labelRepository;

    @GetMapping("/form")
    public String form() {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, Issue issue) {
        issueService.create(loginUser, issue);
        return "redirect:/";
    }

    @GetMapping("")
    public String list() {
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String read(@PathVariable long id, Model model) {
        Issue issue = issueService.findById(id);
        List<Milestone> milestones = milestoneService.findAll();
        List<User> assignees = userService.findAll();
        List<Label> labels = labelRepository.findAll();

        model.addAttribute("issue", issue)
                .addAttribute("milestones", milestones)
                .addAttribute("assignees", assignees)
                .addAttribute("labels", labels);
        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        Issue issue = issueService.findById(id);

        if(!issue.isMatchWriter(loginUser)) {
            throw new UnAuthorizedException();
        }

        model.addAttribute("issue", issue);
        return "/issue/form_update";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, Issue target) {
        log.debug("***** update issue id : {}", id);

        issueService.update(loginUser, id, target);
        return "redirect:/issues/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        log.debug("***** delete issue id : {}", id);

        issueService.delete(loginUser, id);
        return "redirect:/";
    }

    @GetMapping("/{id}/milestones/{milestoneId}")
    public String addToMilestone(@LoginUser User loginUser, @PathVariable long id, @PathVariable long milestoneId) {
        log.debug("***** add issue to milestone : {} to {}", id, milestoneId);

        //TODO: 이슈와 마일스톤 manytomany or manytoone?
        //TODO: 만약 이슈를 다른 마일스톤에 재지정했다면, 기존 마일스톤이 갖고 있던 이슈정보는 삭제해줘야함! < 구현 필요
        issueService.addToMilestone(loginUser, id, milestoneId);
        return "redirect:/issues/{id}";
    }

    @GetMapping("/{id}/users/{assigneeId}")
    public String setAssignee(@LoginUser User loginUser, @PathVariable long id, @PathVariable long assigneeId) {
        log.debug("***** set assignee {} to issue {}", assigneeId, id);

        issueService.setAssignee(loginUser, id, assigneeId);
        return "redirect:/issues/{id}";
    }

    @GetMapping("/{id}/labels/{labelId}")
    public String addLabel(@LoginUser User loginUser, @PathVariable long id, @PathVariable long labelId) {
        log.debug("***** add label {} to issue {}", labelId, id);

        issueService.addLabel(loginUser, id, labelId);
        return "redirect:/issues/{id}";
    }
}
