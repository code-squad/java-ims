package codesquad.web;

import codesquad.CannotDeleteException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import support.domain.Entity;

import javax.validation.Valid;

import static support.domain.Entity.USER;
import static support.domain.Entity.getEntityName;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @GetMapping("/form")
    public String form(@LoginUser User loginUser, Model model) {
        model.addAttribute(getEntityName(USER), loginUser);
        return "/issue/form";
    }

    @PostMapping
    public String create(@LoginUser User loginUser, @Valid IssueDto issueDto) {
        Issue issue = issueService.create(loginUser, issueDto);
        return issue.generateRedirectUri();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute(getEntityName(Entity.ISSUE), issueService.get(id));
        return String.format("/%s/show", getEntityName(Entity.ISSUE));
    }

    @GetMapping("/{id}/edit")
    public String edit(@LoginUser User loginUser, @PathVariable Long id, Model model) {
        model.addAttribute(getEntityName(Entity.ISSUE), issueService.get(loginUser, id));
        return String.format("/%s/edit", getEntityName(Entity.ISSUE));
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable Long id, @Valid IssueDto updateIssueDto) {
        Issue issue = issueService.update(loginUser, id, updateIssueDto);
        return issue.generateRedirectUri();
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable Long id) throws CannotDeleteException {
        issueService.delete(loginUser, id);
        return "redirect:/";
    }
}
