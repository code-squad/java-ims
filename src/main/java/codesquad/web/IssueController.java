package codesquad.web;

import codesquad.domain.Issue;
import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@RequestMapping("/issues")
@Controller
public class IssueController {

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm() {
        return "issue/form";
    }

    @PostMapping("")
    public String create(IssueDto issueDto) {
        Issue newIssue = issueService.save(issueDto);
        return "redirect:/issues/" + newIssue.getId();
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        return "issue/show";
    }

}

