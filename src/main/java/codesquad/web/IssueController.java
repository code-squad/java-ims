package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("issues", issueService.findAll());
        return "issue/list";
    }

    @GetMapping("/{id}")
    public String showIssue(Model model, @PathVariable long id) throws Exception {
        Issue issue = issueService.findById(id).get();
        if (issue.isDeleted()) throw new UnAuthorizedException();
        log.debug("it's gotten issue: {}", issue);
        model.addAttribute("issue", issue);
        return "issue/show";
    }

    @GetMapping("/{id}/form")
    public String updateForm(Model model, @PathVariable long id, @LoginUser User loginUser) throws Exception{
        Issue issue = issueService.findById(id).get();
        if (!loginUser.equals(issue.getWriter())) throw new UnAuthenticationException();
        log.debug("it's gotten issue: {}", issue);
        model.addAttribute("issue", issue);
        return "issue/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, IssueDto issueDto, @LoginUser User loginUser) {
        issueService.update(issueDto, loginUser, id);
        return "redirect:/issues/{id}";
    }
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, @LoginUser User loginUser) {
        issueService.delete(loginUser, id);
        return "redirect:/issues/";
    }

    @GetMapping("form")
    public String createForm(@LoginUser User loginUser) throws Exception{
        if(loginUser.isGuestUser()) return "redirect:/users/login";
        return "issue/form";
    }

    @PostMapping("")
    public String create(IssueDto issueDto, @LoginUser User loginUser) {
        issueService.add(issueDto, loginUser);
        return "redirect:/issues";
    }
}
