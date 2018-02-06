package codesquad.web;

import codesquad.dto.MilestoneDto;
import codesquad.service.MilestoneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping("/milestones")
public class MilestoneController {
    private static final Logger log = LoggerFactory.getLogger(MilestoneController.class);

    @Resource(name = "milestoneService")
    public MilestoneService milestoneService;

    @GetMapping("")
    public String list(Model model){
        model.addAttribute("milestones", milestoneService.findAll());
        return "milestone/list";
    }

    @GetMapping("form")
    public String createForm(){
        return "milestone/form";
    }

    @PostMapping("")
    public String create(MilestoneDto milestoneDto) throws Exception{
        log.debug("mdto in controller: {}", milestoneDto);
        milestoneService.add(milestoneDto);
        return "redirect:/milestones";
    }

}
