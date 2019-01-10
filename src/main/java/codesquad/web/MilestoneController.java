package codesquad.web;

import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private static final Logger log = getLogger(MilestoneController.class);

    @Autowired
    private MilestoneService milestoneService;

    @GetMapping("/list")
    public String createListForm(@LoginUser User loginUser, Model model) {
        model.addAttribute("milestone", milestoneService.findAll());
        return "/milestone/list";
    }

    @GetMapping("")
    public String createForm(@LoginUser User loginUser) {
        return "/milestone/form";
    }

    @PostMapping("")
    public String create(@LoginUser User loginUser, MilestoneDto milestoneDto) {
        log.debug("milestoneDto:{}", milestoneDto);
        milestoneService.create(loginUser, milestoneDto);
        return "redirect:/milestones/list";
    }
}

