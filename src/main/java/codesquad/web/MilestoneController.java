package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
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
        milestoneService.add(loginUser, milestoneDto);
        return "redirect:/milestones";
    }
}
