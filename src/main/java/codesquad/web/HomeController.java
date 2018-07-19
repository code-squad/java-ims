package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.security.HttpSessionUtils;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class HomeController {
    private static final Logger log =  LoggerFactory.getLogger(HomeController.class);
    private static final String USER_SESSION_KEY = "loginedUser";

    //    @Resource(name = "issueRepository")
//    IssueRepository issueRepository;
    @Resource(name = "issueService")
    IssueService issueService;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Issue> issues = issueService.findAll();
        log.debug("issue : {}", issues.toString());
        model.addAttribute("issues", issues);
        return "index";
    }
}
