package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.IssueRepository;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.UserDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.naming.CannotProceedException;
import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private static final Logger log = getLogger(IssueController.class);
    @Resource(name = "issueService")
    private IssueService issueService;

    @GetMapping("")
    public String createIssue(@LoginUser User user) {
        return "issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User user , @Valid IssueDto issueDto) {
        log.debug("IssueDto : {}", issueDto.toString());
        issueService.add(user ,issueDto);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        log.debug("호출되나?");
        model.addAttribute("issue", issueService.findById(id)
                .orElseThrow(UnAuthorizedException::new));
        return "issue/show";
    }

    @GetMapping("/list")
    public String list() {
        return "issue/list";
    }

    @GetMapping("/{id}/update")
    public String update(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id).filter(user -> user.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new));
        return "issue/updateForm";
    }

    @PutMapping("/{id}")
    public String updated(@LoginUser User loginUser, @PathVariable long id, IssueDto target) {
        issueService.update(loginUser, id, target);
        return "redirect:/";
    }
}
