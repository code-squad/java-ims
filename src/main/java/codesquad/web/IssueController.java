package codesquad.web;

import codesquad.domain.Issue;
import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/issues")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @GetMapping("/form")
    public String createForm() {
        return "/issue/form";
    }

    @GetMapping("")
    public String showList() {
        return "redirect:/index";
    }

    @PostMapping("")
    public String create(IssueDto issueDto) {
        issueService.add(issueDto);

        return "redirect:/issues";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        return "issue/show";
    }
}
