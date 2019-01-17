package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/issues")
public class ApiIssueController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/{id}")
    public Issue close(@LoginUser User loginUser, @PathVariable long id) {
        Issue issue = issueService.findById(id);
        issue.closeIssue(loginUser);
        return issue;
    }

}
