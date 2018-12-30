package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log = getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, Issue issue) {
        if (Objects.isNull(loginUser)) {
            return "redirect:/users/login";
        }
        log.debug("loginUser:{}",loginUser);
        issueService.add(loginUser, issue);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findByIssueId(id));
        return "/issue/show";
    }

}
