package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import support.domain.Entity;

import javax.validation.Valid;

import static support.domain.Entity.getEntityName;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    private IssueService issueService;

    @PostMapping
    public String create(@LoginUser User loginUser, @Valid IssueDto issueDto) {
        Issue issue = issueService.create(loginUser, issueDto);
        return issue.generateRedirectUri();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute(getEntityName(Entity.ISSUE), issueService.get(id).toIssue());
        return String.format("/%s/show", getEntityName(Entity.ISSUE));
    }

    @GetMapping("/{id}/edit")
    public String edit(@LoginUser User loginUser, @PathVariable Long id, Model model) {
        model.addAttribute(getEntityName(Entity.ISSUE), issueService.get(loginUser, id).toIssue());
        return String.format("/%s/edit", getEntityName(Entity.ISSUE));
    }
}
