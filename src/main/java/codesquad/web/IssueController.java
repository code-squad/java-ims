package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

@RequestMapping("/issues")
@Controller
public class IssueController {

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm(@LoginUser User loginUser) {
        return "issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto issueDto) {
        Issue newIssue = issueService.save(loginUser, issueDto);
        return "redirect:/issues/" + newIssue.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        return "issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) throws UnAuthenticationException {
        model.addAttribute("issue", issueService.findById(loginUser, id));
        return "issue/updateForm";
    }

    @PutMapping("/{id}")
    public String updateIssue(@LoginUser User loginUser, @PathVariable Long id, IssueDto target) throws UnAuthenticationException {
        Issue issue = issueService.update(loginUser, id, target);
        return String.format("redirect:%s", issue.generateUrl());
    }

    @DeleteMapping("/{id}")
    public String deleteIssue(@LoginUser User loginUser, @PathVariable Long id) throws UnAuthenticationException {
        issueService.delete(loginUser, id);
        return "redirect:/";
    }

}

