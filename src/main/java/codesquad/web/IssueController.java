package codesquad.web;

import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String form() {
        return "/issue/form";
    }

    @PostMapping("")
    public String create(IssueDto issueDto) {
        issueService.add(issueDto);
        return "redirect:/issues";
    }

}
