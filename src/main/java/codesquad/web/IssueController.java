package codesquad.web;

import codesquad.domain.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueRepository issueRepository;

    @GetMapping
    public String issueForm() {
        return "/issue/form";
    }

    @GetMapping("/{id}")
    public String issueDetail(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueRepository.findById(id).orElse(null));
        return "/issue/show";
    }
}
