package codesquad.web;

import codesquad.UnAuthenticationException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.HttpSessionUtils;
import codesquad.service.IssueService;
import codesquad.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String show (Model model) {
        List<Issue> issueList = issueService.findAllIssues();
        model.addAttribute("issues", issueList);

        return "index";
    }

    @PostMapping("/login")
    public String signIn (String userId, String password) {
        try {
            userService.login(userId, password);
        } catch (UnAuthenticationException e) {
            return "user/login-failed";
        }

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String signOut (HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

        return "redirect:/";
    }
}
