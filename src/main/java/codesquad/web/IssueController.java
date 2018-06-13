package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @PostMapping
    public String create(@LoginUser User user, @Valid IssueDto issueDto) {
        Issue issue = issueService.create(user, issueDto);
        return issue.generateRedirectUri();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        Issue issue = issueService.get(id).toIssue();
        model.addAttribute(issue.getEntityName(), issue);
        return String.format("/%s/show", issue.getEntityName());
    }
}
