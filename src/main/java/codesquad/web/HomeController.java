package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    private static final Logger log =  LoggerFactory.getLogger(HomeController.class);
    private static final String USER_SESSION_KEY = "loginedUser";

    @Resource(name = "issueRepository")
    IssueRepository issueRepository;

    @GetMapping("/")
    public String home(Model model) {
        List<Issue> issues = issueRepository.findAll();
        model.addAttribute("issues", issues);
        return "index";
    }
}
