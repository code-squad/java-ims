package codesquad.web;

import codesquad.UnAuthorizedException;
import codesquad.domain.Issue;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.security.LoginUser;
import codesquad.service.AttachmentService;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import codesquad.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
@RequestMapping("/issues")
public class IssueController {
    private static final Logger logger = LoggerFactory.getLogger(IssueController.class);

    @Autowired
    private IssueService issueService;

    @Autowired
    private MilestoneService milestoneService;

    @Autowired
    private UserService userService;

    @Autowired
    private AttachmentService attachmentService;

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        return "/issue/form";
    }

    @GetMapping("")
    public String showList() {
        return "redirect:/";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, IssueDto issueDto) {
        Issue issue = issueService.add(loginUser, issueDto);
        return String.format("redirect:/issues/%d", issue.getId());
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Long id, Model model) {
        model.addAttribute("issue", issueService.findById(id));
        model.addAttribute("milestones", milestoneService.findAll());
        model.addAttribute("users", userService.findAll());
        return "/issue/show";
    }

    @PutMapping("/{id}")
    public String update(@LoginUser User loginUser, @PathVariable Long id, IssueDto issueDto) {
        issueService.update(loginUser, id, issueDto);

        return String.format("redirect:/issues/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@LoginUser User loginUser, @PathVariable Long id) {
        issueService.delete(loginUser, id);

        return "redirect:/";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@LoginUser User loginUser, @PathVariable Long id, Model model) {
        try {
            model.addAttribute("issue", issueService.findByIdForEdit(loginUser, id));
            return "/issue/updateForm";
        } catch (UnAuthorizedException e) {
            model.addAttribute("error", e.getMessage());
            return "/user/login";
        }
    }

    @PostMapping("/{id}/attachments")
    public String upload(@LoginUser User loginUser, @PathVariable Long id, MultipartFile file) throws Exception {
        logger.debug("original file name: {}", file.getOriginalFilename());
        logger.debug("contenttype: {}", file.getContentType());

        // TODO MultipartFile로 전달된 데이터를 서버의 특정 디렉토리에 저장하고, DB에 관련 정보를 저장한다.
        attachmentService.save(loginUser, id, file);

        return "redirect:/";
    }

    @PutMapping("/{issueId}/setMilestone/{milestoneId}")
    public String setMilestone(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long milestoneId) {
        issueService.setMilestone(loginUser, issueId, milestoneId);

        return String.format("redirect:/issues/%d", issueId);
    }

    @PutMapping("/{issueId}/setAssignee/{assigneeId}")
    public String setAssignee(@LoginUser User loginUser, @PathVariable Long issueId, @PathVariable Long assigneeId) {
        issueService.setAssignee(loginUser, issueId, assigneeId);

        return String.format("redirect:/issues/%d", issueId);
    }
}
