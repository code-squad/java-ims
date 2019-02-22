package codesquad.web;

import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log = getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("/form")
    public String form(){
        return "/issue/form";
    }


    @PostMapping("")
    public String create(IssueDto issueDto){
        issueService.add(issueDto);
        return "redirect:/issues/list";
    }

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("issues", issueService.findAll());
        return "/index";
    }

    @GetMapping("/show/{id}")
    public String showForm(@PathVariable Long id, Model model){
        model.addAttribute("issues", issueService.findById(id));
        return "/issue/show";
    }
}
