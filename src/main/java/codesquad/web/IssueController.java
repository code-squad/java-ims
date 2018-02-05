package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        return "/issue/form";
    }

    @GetMapping("")
    public String showList() {
        return "redirect:/";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto issueDto, Model model) {
        try {
            Issue issue = issueService.add(loginUser, issueDto);
            return String.format("redirect:/issues/%d", issue.getId());
        } catch (UnAuthenticationException e) {
            model.addAttribute("error", e.getMessage());
            return "/users/login";
        }
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        return "/issue/show";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable Long id, IssueDto issueDto) {
        issueService.update(loginUser, id, issueDto);

        return String.format("redirect:/issues/%d", id);
    }
}
