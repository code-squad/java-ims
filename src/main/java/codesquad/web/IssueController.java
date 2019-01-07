package codesquad.web;

import codesquad.domain.Contents;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issue")
public class IssueController {
    private static final Logger log = getLogger(IssueController.class);

    @Autowired
    IssueService issueService;

    @GetMapping("")
    public String form(@LoginUser User loginUser) {
        return "issue/form";
    }

    @PostMapping("")
    public String create(@Valid IssueDto issueDto, @LoginUser User loginUser) {
        issueService.add(issueDto, loginUser);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        return "issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable long id, Model model) {
        Issue updateIssue = issueService.oneself(loginUser, id);
        model.addAttribute("updateIssue", updateIssue);
        return "issue/updateform";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable long id, Contents updateIssueContents) {
        issueService.update(loginUser, id, updateIssueContents);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        log.debug("삭제");
        issueService.delete(loginUser, id);
        return "index";
    }
}
