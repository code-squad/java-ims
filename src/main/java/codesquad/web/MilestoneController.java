package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        return "/milestone/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, Milestone milestone) {
        milestoneService.add(milestone);
        return "redirect:/milestones";
    }

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("milestones", milestoneService.findAll());
        return "/milestone/list";
    }
}
