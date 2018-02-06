package codesquad.web;

import codesquad.domain.Issue;
import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.IssueDto;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.IssueService;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private static final Logger logger = LoggerFactory.getLogger(MilestoneController.class);

    @Autowired
    private MilestoneService milestoneService;

    @GetMapping("/form")
    public String createForm(@LoginUser User user) {
        return "/milestone/form";
    }

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("milestones", milestoneService.findAll());
        return "/milestone/list";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, MilestoneDto milestoneDto) {
        Milestone milestone = milestoneService.add(loginUser, milestoneDto);
        return String.format("redirect:/milestones/%d", milestone.getId());
    }
//
//    @GetMapping("/{id}")
//    public String show(@PathVariable Long id, Model model) {
//        model.addAttribute("issue", issueService.findByIdForEdit(id));
//        return "/issue/show";
//    }
//
//    @PutMapping("/{id}")
//    public String update(@LoginUser User loginUser, @PathVariable Long id, IssueDto issueDto) {
//        issueService.update(loginUser, id, issueDto);
//
//        return String.format("redirect:/issues/%d", id);
//    }
//
//    @DeleteMapping("/{id}")
//    public String delete(@LoginUser User loginUser, @PathVariable Long id) {
//        issueService.delete(loginUser, id);
//
//        return "redirect:/";
//    }
}
