package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MileStoneService;
import codesquad.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("issue")
public class IssueController {
    private static final Logger log =  LoggerFactory.getLogger(IssueController.class);

    @Resource(name = "issueService")
    IssueService issueService;

    @Resource(name = "userService")
    UserService userService;

    @Resource(name = "mileStoneService")
    MileStoneService mileStoneService;

    @GetMapping("form")
    public String form() {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto issueDto) {
        log.info("create method called");
        issueService.add(loginUser, issueDto);
        return "redirect:/";
    }

    @GetMapping("{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        model.addAttribute("user", userService.findAll());
        model.addAttribute("milestones", mileStoneService.findAll());
        return "/issue/show";
    }

    @PutMapping("{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, IssueDto target) throws CannotDeleteException {
        issueService.update(loginUser, id, target);
        return "redirect:/";
    }

    @DeleteMapping("{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) throws CannotDeleteException {
        issueService.delete(loginUser, id);
        return "redirect:/";
    }

    @GetMapping("{id}/setAssignee/{userId}")
    public String setAssignee(@PathVariable long id, @PathVariable long userId) {
        log.info("id : {}, userId : {}", id, userId);
        return "redirect:/";
    }

    @PutMapping("/{issueId}/setMilestone/{id}")
    public String update(@PathVariable long issueId, @PathVariable long id) {
        
        return "redirect:/";
    // /issue/{{issue.id}}/setMilestone/{{id}}
    }
}
