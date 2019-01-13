package codesquad.web;

import codesquad.domain.milestone.MilestoneBody;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String list(Model model) {
        model.addAttribute("milestones", milestoneService.findAll());
        return "milestone/list";
    }

    @GetMapping("/form")
    public String form() {
        return "milestone/form";
    }

    @PostMapping("")
    public String create(@Valid MilestoneBody milestoneBody, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return "redirect:/milestones/form";
        milestoneService.create(milestoneBody);
        return "redirect:/milestones";
    }
}
