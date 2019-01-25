package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/issues")
public class IssueController {

    private static final Logger log = getLogger(IssueController.class);
    @Resource(name = "issueService")
    private IssueService issueService;

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @Resource(name = "labelService")
    private LabelService labelService;

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "answerService")
    private AnswerService answerService;

    @Resource(name = "multipartFileService")
    private MultipartFileService multipartFileService;

    @GetMapping("")
    public String createIssue(@LoginUser User user) {
        return "issue/form";
    }

    @PostMapping("")
    public String create(@LoginUser User user , @Valid IssueDto issueDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                log.debug("issue create error : {}", error.getDefaultMessage());
            }
            return "redirect:/issues";
        }
        log.debug("IssueDto : {}", issueDto.toString());
        issueService.add(user ,issueDto);
        return "redirect:/";
    }

    @GetMapping("/{id}")
    public String showDetail(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id)
                .orElseThrow(UnAuthorizedException::new));
        model.addAttribute("milestones", milestoneService.findAll());
        model.addAttribute("labels", labelService.findAll());
        model.addAttribute("assignees", userService.findAll());
        return "issue/show";
    }

    @GetMapping("/{id}/update")
    public String update(@LoginUser User loginUser, @PathVariable long id, Model model) {
        model.addAttribute("issue", issueService.findById(id).filter(user -> user.isOwner(loginUser))
                .orElseThrow(UnAuthorizedException::new));
        return "issue/updateForm";
    }

    @PutMapping("/{id}")
    public String updated(@LoginUser User loginUser, @PathVariable long id, IssueDto target, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                log.debug("update error : {}",error.getDefaultMessage());
            }
            return "redirect:/users/{id}/update";
        }
        issueService.update(loginUser, id, target);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable long id) {
        issueService.deleteIssue(loginUser, id);
        return "redirect:/";
    }
}
