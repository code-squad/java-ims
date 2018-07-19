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
import javax.validation.Valid;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log =  LoggerFactory.getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        log.debug("issue form");
        return "/issue/form";
    }

    @PostMapping()
    public String create(@LoginUser User user, @RequestBody Issue issue) {
        log.debug("issue : {}", issue.toString());
        issueService.save(user, issue);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
         model.addAttribute("issue", issueService.findById(id));
        return "/issue/show";
    }

    @GetMapping("/{id}/form")
    String updateForm(@LoginUser User user, @PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        return "/issue/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User user, @PathVariable long id, @RequestBody Issue updateIssue) {
        log.debug("issue update : {}", updateIssue);
        issueService.update(id, updateIssue);
        return String.format("redirect:/issues/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User user, @PathVariable long id) {
        issueService.delete(id);
        return "redirect:/";
    }
}
