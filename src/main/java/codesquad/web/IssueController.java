package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log = getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("")
    public String createForm(@LoginUser User loginUser) {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto newIssue) {
        if (Objects.isNull(loginUser)) {
            return "redirect:/login";
        }
        log.debug("loginUser:{}",loginUser);
        issueService.create(loginUser, newIssue);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findByIssueId(id)._toIssueDto());
        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    public String modifyForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        Issue issue = issueService.findByIssueId(id);
        if (issue.isOwner(loginUser)) {
            model.addAttribute("issue", issue._toIssueDto());
            return "/issue/updateForm";
        }
        return String.format("redirect:/issues/%d",id);
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
}
