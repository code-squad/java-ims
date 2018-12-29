package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.IssueRepository;
import codesquad.dto.IssueDto;
import codesquad.service.IssueService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.naming.CannotProceedException;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private static final Logger log = getLogger(IssueController.class);
    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("")
    public String createIssue() {
        return "issue/form";
    }

    @PostMapping("")
    public String create(IssueDto issueDto) {
        log.debug("IssueDto : {}", issueDto.toString());
        issueService.add(issueDto);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id)
                .orElseThrow(UnAuthorizedException::new));
        return "issue/show";
    }

    @GetMapping("list")
    public String list() {
        return "issue/list";
    }
}
