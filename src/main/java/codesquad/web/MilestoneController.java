package codesquad.web;


import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
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
    MilestoneService milestoneService;

    @GetMapping("")
    public String showList(Model model) {
        model.addAttribute("milestones", milestoneService.findAll());
        return "milestone/list";
    }

    @GetMapping("/form")
    public String createForm(@LoginUser User loginUser) {
        return "milestone/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, MilestoneDto milestoneDto) {
        milestoneService.save(loginUser, milestoneDto);
        return "redirect:/";
    }
}
