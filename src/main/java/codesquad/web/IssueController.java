package codesquad.web;

import codesquad.domain.Issue;
import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
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
    public String showIssue(Model model, @PathVariable long id) {
        Issue issue = issueService.findById(id).get();
        log.debug("it's gotten issue: {}", issue);
        model.addAttribute("issue", issue);
        return "issue/show";
    }

    @GetMapping("form")
    public String createForm() {
        return "issue/form";
    }

    @PostMapping("")
    public String create(IssueDto issueDto) {
        issueService.add(issueDto);
        return "redirect:/issues";
    }
}
