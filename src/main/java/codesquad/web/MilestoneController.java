package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.validation.Valid;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private static final Logger log = getLogger(MilestoneController.class);

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;

    @GetMapping("")
    public String create() {
        return "/milestone/form";
    }

    @GetMapping("/list")
    public String list(Milestone milestone, Model model) {
        model.addAttribute("milestone", milestoneService.findAll());
        return "/milestone/list";
    }

    @PostMapping("")
    public String createMilesone(@LoginUser User user, @Valid Milestone milestone) {
        milestoneService.create(user ,milestone);
        return "/milestone/list";
    }

}
