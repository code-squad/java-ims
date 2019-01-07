package codesquad.web;

import codesquad.UnAuthorizedException;
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
        model.addAttribute("issue", issue);
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
}
