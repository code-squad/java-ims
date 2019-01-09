package codesquad.web;

import codesquad.domain.milestone.Milestone;
import codesquad.domain.user.User;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/milestones")
public class milestoneController {

    @Autowired
    MilestoneService milestoneService;

    @GetMapping("")
    public String list(Model model) {
        model.addAttribute("milestones", milestoneService.findAll());
        return "milestone/list";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, Milestone milestone) {
        milestoneService.add(milestone, loginUser);
        return "redirect:/milestones";
    }

    @GetMapping("/form")
    public String milstoneForm(@LoginUser User loginUser) {
        return "milestone/form";
    }
}

