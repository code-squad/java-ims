package codesquad.web;

import codesquad.CannotShowException;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger log = LoggerFactory.getLogger(IssueController.class);

    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @Resource(name = "commentService")
    private CommentService commentService;

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        log.debug("issue form");
        return "/issue/form";
    }

    @PostMapping()
    public String create(@LoginUser User user, IssueDto issueDto) {
        log.debug("issue : {}", issueDto.toString());
        issueService.save(user, issueDto);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) throws CannotShowException {
        // TODO 지워진 issue를 url 조작을 통해 사용자가 볼 수 없어야 한다.
        // issueService에서 issue를 찾고 deleted 여부를 판단하여 exception을 발생시킨다?
//        try {
            model.addAttribute("issue", issueService.findById(id));
//        } catch (CannotShowException e) {
//
//        }
        return "/issue/show";
    }

    @PostMapping("/{id}/form")
    String updateForm(@LoginUser User user, @PathVariable long id, Model model) {
        try {
            model.addAttribute("issue", issueService.findById(id));
        } catch (CannotShowException e) {
            log.debug("CannotShowException message : {}", e.getMessage());
        }
        return "/issue/updateForm";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User user, @PathVariable long id, IssueDto updateIssueDto) {
        issueService.update(id, user, updateIssueDto);
        return String.format("redirect:/issues/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User user, @PathVariable long id) {
        issueService.delete(id);
        return "redirect:/";
    }
}
