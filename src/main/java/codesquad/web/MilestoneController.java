package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.domain.User;
import codesquad.dto.MilestoneDto;
import codesquad.security.LoginUser;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);
    @Resource(name = "milestoneService")
    MilestoneService milestoneService;

    @GetMapping("/form")
    public String form(@LoginUser User loginUser) {
        return "/milestone/form";
    }

    @PostMapping("")
    public ModelAndView create(@LoginUser User loginUser, MilestoneDto milestoneDto, ModelMap model) {
        Milestone milestone = new Milestone(milestoneDto);
        Milestone savedMilestone = milestoneService.addMilestone(milestone);
        log.debug("milestone id : {}", savedMilestone.getId());

        model.addAttribute("attribute", savedMilestone.getId());

        return new ModelAndView("redirect:/milestones", model);
    }

    @GetMapping("")
    public String list(Model model) {
        List<Milestone> milestones = milestoneService.getMilestones();
        model.addAttribute("milestones", milestones);

        return "/milestone/list";
    }
}
