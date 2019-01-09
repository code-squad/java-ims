package codesquad.web;

import codesquad.domain.Milestone;
import codesquad.service.MilestoneService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/milestone")
public class MilestoneController {
    private static final Logger log = LogManager.getLogger(MilestoneController.class);

    @Resource(name = "milestoneService")
    private MilestoneService milestoneService;


    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("milestones", milestoneService.findAll());
        return "/milestone/list";
    }

    @GetMapping("/form")
    public String createForm() {
        return "/milestone/form";
    }

    @PostMapping("")
    public String create(Milestone milestone) {
        log.debug(milestone.toString());
        milestoneService.add(milestone);
        return "redirect:/milestone/list";
    }
}
