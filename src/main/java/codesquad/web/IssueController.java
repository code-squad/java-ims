package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.ContentsBody;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.LabelService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/issue")
public class IssueController {
    private static final Logger log = LogManager.getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @Resource(name = "userService")
    private UserService userService;

    @GetMapping("/form")
    public String createForm(@LoginUser User loginUser) {

        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, ContentsBody contentsBody) {
        issueService.add(loginUser, contentsBody);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        Issue issue = issueService.findById(id).orElseThrow(UnAuthorizedException::new);
        model.addAttribute("issue",issue);
        model.addAttribute("milestones",milestoneService.findAll());
        model.addAttribute("labels", labelService.findAll());
        model.addAttribute("assignees",userService.findAll());
        //Todo 라벨, 어사이니 모델로 줘야함 -> 이슈 본인것 담고 나머지 모델로 담을까?
        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        log.debug("업데이트폼");
        Issue issue = issueService.findById(id).orElseThrow(UnAuthorizedException::new);
        model.addAttribute("issue",issue);
        return "/issue/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, Model model, ContentsBody contentsBody) {
        Issue issue = issueService.update(id,loginUser, contentsBody);
        log.debug("이슈데이터: {}",issue);
        model.addAttribute("issue",issue);
        return "redirect:/issue/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        issueService.deleted(id,loginUser);
        return "redirect:/";
    }


    //todo 이넘을 쓰면 하나로 할수 있을까?
    @GetMapping("/{id}/setMilestone/{milestoneId}")
    public String setMilestone(@PathVariable Long id, @PathVariable Long milestoneId) {
        //Todo login 추가해야함
        issueService.updateMilestone(id,milestoneId);
        return "redirect:/issue/{id}";
    }

    @GetMapping("/{id}/setLabel/{labelId}")
    public String setLabel(@PathVariable Long id, @PathVariable Long labelId) {
        //Todo login 추가해야함
        issueService.updateLabel(id,labelId);
        return "redirect:/issue/{id}";
    }

    @GetMapping("/{id}/setAssignee/{userId}")
    public String setAssignee(@PathVariable Long id, @PathVariable Long userId) {
        //Todo login 추가해야함
        issueService.updateAssignee(id,userId);
        return "redirect:/issue/{id}";
    }
}
